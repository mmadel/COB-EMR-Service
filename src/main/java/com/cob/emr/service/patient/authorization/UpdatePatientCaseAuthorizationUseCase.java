package com.cob.emr.service.patient.authorization;

import com.cob.emr.entity.patient.authorization.PatientCaseAuthorization;
import com.cob.emr.repositories.patient.authorization.PatientCaseAuthorizationRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Component
public class UpdatePatientCaseAuthorizationUseCase {
    @Autowired
    PatientCaseRepository patientCaseRepository;
    @Autowired
    PatientCaseAuthorizationRepository patientCaseAuthorizationRepository;

    public void update(String dateOfService, Long caseId, String zoneIdString) {
        final ZoneId zone;
        try {
            zone = ZoneId.of(zoneIdString);
        } catch (DateTimeException ex) {
            throw new IllegalArgumentException("Invalid zoneId: " + zoneIdString, ex);
        }

        // parse DOS
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        final LocalDate dosLocalDate;
        try {
            dosLocalDate = LocalDate.parse(dateOfService, fmt);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("dateOfService must be MM/dd/yyyy: " + dateOfService, ex);
        }

        // DOS as instant (start-of-day in requested zone)
        Instant dosInstant = dosLocalDate.atStartOfDay(zone).toInstant();

        Optional<List<PatientCaseAuthorization>> opt = patientCaseAuthorizationRepository.findByPatientCase_Id(caseId);
        if (!opt.isPresent() || opt.get().isEmpty()) {
            System.out.println("No authorizations for caseId=" + caseId);
            return;
        }
        PatientCaseAuthorization authorization = opt.get().get(0);

        // normalize stored epoch (seconds vs millis)
        long raw = authorization.getEffectiveStart();
        long authMillis = normalizeEpochToMillis(raw);
        Instant authInstant = Instant.ofEpochMilli(authMillis);

        // --- OPTION A: compare instants (exact moment) ---
        boolean instantsEqual = dosInstant.equals(authInstant);
        if (instantsEqual) {
            System.out.println("Instants equal (exact match): dos=" + dosInstant + " auth=" + authInstant);
        } else {
            System.out.println("Instants NOT equal: dos=" + dosInstant + " auth=" + authInstant);
        }

        // --- OPTION B (recommended for "same calendar day"): compare LocalDate in the same zone ---
        LocalDate authLocalDateInZone = authInstant.atZone(zone).toLocalDate();
        if (dosLocalDate.equals(authLocalDateInZone)) {
            System.out.println("Calendar date EQUAL in zone " + zone + " (dos=" + dosLocalDate + ", auth=" + authLocalDateInZone + ")");
            // treat as equal
        } else {
            System.out.println("Calendar date DIFFERENT in zone " + zone + " (dos=" + dosLocalDate + ", auth=" + authLocalDateInZone + ")");
        }
    }
    // Helper to convert seconds -> millis if needed
    private long normalizeEpochToMillis(long epochValue) {
        // if epochValue looks like seconds (e.g. ~1_700_000_000), multiply by 1000
        // threshold: 1_000_000_000_000L ~= Sat Sep 09 2001 (millis)
        if (Math.abs(epochValue) < 1_000_000_000_000L) {
            return epochValue * 1000L;
        }
        return epochValue;
    }
}
