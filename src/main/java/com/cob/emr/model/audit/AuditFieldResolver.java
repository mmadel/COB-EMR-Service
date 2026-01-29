package com.cob.emr.model.audit;

import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import com.cob.emr.repositories.calendar.CalendarRepository;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class AuditFieldResolver {

    private static final Set<String> RESOLVABLE_FIELDS = Set.of(
            "appointmentType", "clinic", "patient", "patientCase", "calendar"
    );

    private static final Set<String> UUID_FIELDS = Set.of(
            "therapyUUID"
    );

    private static final Set<String> DATE_FIELDS = Set.of(
            "startDate", "endDate", "createdAt", "updatedAt"
    );

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of("UTC"));

    private final AppointmentTypeRepository appointmentTypeRepository;
    private final ClinicRepository clinicRepository;
    private final PatientRepository patientRepository;
    private final PatientCaseRepository patientCaseRepository;
    private final CalendarRepository calendarRepository;
    private final ClinicalEMRUserRepository clinicalEMRUserRepository;

    public AuditFieldResolver(AppointmentTypeRepository appointmentTypeRepository,
                              ClinicRepository clinicRepository,
                              PatientRepository patientRepository,
                              PatientCaseRepository patientCaseRepository,
                              CalendarRepository calendarRepository,
                              ClinicalEMRUserRepository clinicalEMRUserRepository) {
        this.appointmentTypeRepository = appointmentTypeRepository;
        this.clinicRepository = clinicRepository;
        this.patientRepository = patientRepository;
        this.patientCaseRepository = patientCaseRepository;
        this.calendarRepository = calendarRepository;
        this.clinicalEMRUserRepository = clinicalEMRUserRepository;
    }

    public boolean isResolvable(String fieldName) {
        return RESOLVABLE_FIELDS.contains(fieldName)
                || DATE_FIELDS.contains(fieldName)
                || UUID_FIELDS.contains(fieldName);
    }

    public ResolverCache createCache() {
        return new ResolverCache();
    }

    public Object resolve(String fieldName, Object value, ResolverCache cache) {
        if (value == null) return null;

        if (DATE_FIELDS.contains(fieldName)) {
            return formatDate(value);
        }

        if (UUID_FIELDS.contains(fieldName)) {
            String uuid = value.toString();
            String cacheKey = fieldName + ":" + uuid;
            if (cache.has(cacheKey)) return cache.get(cacheKey);
            String resolved = lookupByUUID(fieldName, uuid);
            cache.put(cacheKey, resolved);
            return resolved;
        }

        Long id = toLong(value);
        if (id == null) return value;

        String cacheKey = fieldName + ":" + id;
        if (cache.has(cacheKey)) return cache.get(cacheKey);

        String resolved = lookupDisplayName(fieldName, id);
        cache.put(cacheKey, resolved);
        return resolved;
    }

    private String formatDate(Object value) {
        Long millis = toLong(value);
        if (millis == null) return value.toString();
        return DATE_FORMATTER.format(Instant.ofEpochMilli(millis));
    }

    private String lookupDisplayName(String fieldName, Long id) {
        try {
            switch (fieldName) {
                case "appointmentType":
                    return appointmentTypeRepository.findById(id)
                            .map(AppointmentType::getName).orElse("ID:" + id);
                case "clinic":
                    return clinicRepository.findById(id)
                            .map(Clinic::getName).orElse("ID:" + id);
                case "patient":
                    return patientRepository.findById(id)
                            .map(p -> buildName(p.getFirstName(), p.getLastName())).orElse("ID:" + id);
                case "patientCase":
                    return patientCaseRepository.findById(id)
                            .map(PatientCase::getTitle).orElse("ID:" + id);
                case "calendar":
                    return calendarRepository.findById(id)
                            .map(CalendarEntity::getName).orElse("ID:" + id);
                default:
                    return "ID:" + id;
            }
        } catch (Exception e) {
            return "ID:" + id;
        }
    }

    private String lookupByUUID(String fieldName, String uuid) {
        try {
            if ("therapyUUID".equals(fieldName)) {
                return clinicalEMRUserRepository.findByUUID(uuid)
                        .map(u -> buildName(u.getFirstName(), u.getLastName()))
                        .orElse(uuid);
            }
        } catch (Exception e) {
            // fallback to raw UUID
        }
        return uuid;
    }

    private String buildName(String first, String last) {
        StringBuilder sb = new StringBuilder();
        if (first != null && !first.isBlank()) sb.append(first);
        if (last != null && !last.isBlank()) sb.append(" ").append(last);
        return sb.length() > 0 ? sb.toString().trim() : null;
    }

    private Long toLong(Object value) {
        if (value instanceof Long) return (Long) value;
        if (value instanceof Integer) return ((Integer) value).longValue();
        if (value instanceof Number) return ((Number) value).longValue();
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static class ResolverCache {
        private final Map<String, Object> cache = new HashMap<>();

        public boolean has(String key) { return cache.containsKey(key); }
        public Object get(String key) { return cache.get(key); }
        public void put(String key, Object value) { cache.put(key, value); }
    }
}
