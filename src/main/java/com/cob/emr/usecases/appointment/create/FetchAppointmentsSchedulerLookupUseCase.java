package com.cob.emr.usecases.appointment.create;

import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class FetchAppointmentsSchedulerLookupUseCase {
    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientCaseRepository patientCaseRepository;
    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    public Map<Long, Clinic> clinicsLookups;
    public Map<Long, Patient> patientsLookups;
    public Map<Long, PatientCase> patientCaseLookups;
    public Map<Long, AppointmentType> appointmentTypeLookups;

    public void fetch(List<SchedulerAppointment> appointments) {
        clinicsLookups = fetchClinicsLookup(appointments);
        patientsLookups = fetchPatientsLookup(appointments);
        patientCaseLookups = fetchPatientCaseLookups(appointments);
        appointmentTypeLookups = fetchAppointmentTypeLookups(appointments);
    }

    private Map<Long, Clinic> fetchClinicsLookup(List<SchedulerAppointment> appointments) {
        Set<Long> clinicIds = appointments.stream()
                .map(SchedulerAppointment::getClinicId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return StreamSupport.stream(clinicRepository.findAllById(clinicIds).spliterator(), false)
                .collect(Collectors.toMap(Clinic::getId, Function.identity()));
    }

    private Map<Long, Patient> fetchPatientsLookup(List<SchedulerAppointment> appointments) {
        Set<Long> patientIds = appointments.stream()
                .map(SchedulerAppointment::getPatientId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return StreamSupport.stream(patientRepository.findAllById(patientIds).spliterator(), false)
                .collect(Collectors.toMap(Patient::getId, Function.identity()));
    }

    private Map<Long, PatientCase> fetchPatientCaseLookups(List<SchedulerAppointment> appointments) {
        Set<Long> patientCasesIds = appointments.stream()
                .map(SchedulerAppointment::getPatientCaseId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return StreamSupport.stream(patientCaseRepository.findAllById(patientCasesIds).spliterator(), false)
                .collect(Collectors.toMap(PatientCase::getId, Function.identity()));
    }

    public Map<Long, AppointmentType> fetchAppointmentTypeLookups(List<SchedulerAppointment> appointments) {
        Set<Long> appointmentTypesIds = appointments.stream()
                .map(SchedulerAppointment::getAppointmentTypeId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return StreamSupport.stream(appointmentTypeRepository.findAllById(appointmentTypesIds).spliterator(), false)
                .collect(Collectors.toMap(AppointmentType::getId, Function.identity()));
    }
}
