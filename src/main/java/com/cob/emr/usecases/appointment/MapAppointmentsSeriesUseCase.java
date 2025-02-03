package com.cob.emr.usecases.appointment;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.enums.AppointmentStructure;
import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class MapAppointmentsSeriesUseCase {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    AppointmentTypeRepository appointmentTypeRepository;
    @Autowired
    PatientCaseRepository patientCaseRepository;

    private Clinic clinic;
    private AppointmentType appointmentType;
    private PatientCase patientCase;

    public Boolean map(List<Appointment> seriesAppointments, SchedulerAppointment schedulerAppointment) {
        fetchSchedulerAppointmentEntities(schedulerAppointment.getClinicId(),
                schedulerAppointment.getAppointmentTypeId(),
                schedulerAppointment.getPatientCaseId(),
                schedulerAppointment.getAppointmentStructure());

        AtomicBoolean isUpdated = new AtomicBoolean(false);
        seriesAppointments.stream().forEach(appointment -> {
            if (clinic != null && (!Objects.equals(appointment.getClinic().getId(), schedulerAppointment.getClinicId()))) {
                appointment.setClinic(clinic);
                isUpdated.set(true);
            }
            if (appointmentType != null && (!Objects.equals(appointment.getAppointmentType().getId(), schedulerAppointment.getAppointmentTypeId()))) {
                appointment.setAppointmentType(appointmentType);
                isUpdated.set(true);
            }
            if (patientCase != null && (!Objects.equals(appointment.getPatientCase().getId(), schedulerAppointment.getPatientCaseId()))) {
                appointment.setPatientCase(patientCase);
                isUpdated.set(true);
            }

            if (!Objects.equals(appointment.getTitle(), schedulerAppointment.getTitle())) {
                appointment.setTitle(schedulerAppointment.getTitle());
                isUpdated.set(true);
            }
            if (!Objects.equals(appointment.getNote(), schedulerAppointment.getNote())) {
                appointment.setNote(schedulerAppointment.getNote());
                isUpdated.set(true);
            }
            if (!Objects.equals(appointment.getTherapyUUID(), schedulerAppointment.getTherapyUUID())) {
                appointment.setTherapyUUID(schedulerAppointment.getTherapyUUID());
                isUpdated.set(true);
            }
            if (!Objects.equals(appointment.getAppointmentStatus(), schedulerAppointment.getAppointmentStatus())) {
                appointment.setAppointmentStatus(schedulerAppointment.getAppointmentStatus());
                isUpdated.set(true);
            }
            if (!Objects.equals(appointment.getStartDate(), schedulerAppointment.getStartDate())) {
                appointment.setStartDate(changeDate(appointment.getStartDate(),schedulerAppointment.getStartDate()));
                isUpdated.set(true);
            }
            if (!Objects.equals(appointment.getEndDate(), schedulerAppointment.getEndDate())) {
                appointment.setEndDate(changeDate(appointment.getEndDate(),schedulerAppointment.getEndDate()));
                isUpdated.set(true);
            }
        });
        return isUpdated.get();
    }

    private void fetchSchedulerAppointmentEntities(Long clinicId, Long appointmentTypeId, Long patientCaseId, AppointmentStructure appointmentStructure) {
        clinic = null;
        appointmentType=null;
        patientCase = null;
        switch (appointmentStructure) {
            case Full:
                clinic = clinicRepository.findById(clinicId)
                        .orElseThrow(() -> new IllegalArgumentException("Clinic with id " + clinicId + " not found"));
                appointmentType = appointmentTypeRepository.findById(appointmentTypeId)
                        .orElseThrow(() -> new IllegalArgumentException("Appointment Type with id " + appointmentTypeId + " not found"));
                patientCase = patientCaseRepository.findById(patientCaseId)
                        .orElseThrow(() -> new IllegalArgumentException("Patient case with id " + patientCaseId + " not found "));
                break;
            case Block:
                appointmentType = appointmentTypeRepository.findById(appointmentTypeId)
                        .orElseThrow(() -> new IllegalArgumentException("Appointment Type with id " + appointmentTypeId + " not found"));
        }
    }

    private Long changeDate(long appointmentStart, long newAppointmentStart) {
        String startTime = getHourAndMinutes(newAppointmentStart);
        Instant instant = Instant.ofEpochMilli(appointmentStart);
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime appointmentDateTime = LocalDateTime.ofInstant(instant, zoneId);


        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime newTime = LocalTime.parse(startTime, timeFormatter);


        LocalDateTime updatedDateTime = appointmentDateTime.withHour(newTime.getHour())
                .withMinute(newTime.getMinute())
                .withSecond(0)
                .withNano(0);

        return updatedDateTime.atZone(zoneId).toInstant().toEpochMilli();
    }

    public static String getHourAndMinutes(long timestamp) {
        // Convert milliseconds to LocalDateTime in system default timezone
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zoneId = ZoneId.systemDefault();
        LocalTime time = LocalDateTime.ofInstant(instant, zoneId).toLocalTime();

        // Format the time as HH:mm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }
}
