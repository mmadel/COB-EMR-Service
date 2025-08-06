package com.cob.emr.usecases.appointment.create;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.model.appointment.SchedulerAppointment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CreateAppointmentEntityUseCase {
    private Map<Long, Clinic> clinicsLookups;
    private Map<Long, Patient> patientsLookups;
    private Map<Long, PatientCase> patientCaseLookups;
    private Map<Long, AppointmentType> appointmentTypeLookups;
    @Autowired
    ModelMapper mapper;

    public Appointment create(SchedulerAppointment appointment) {
        Appointment toBeCreated = mapper.map(appointment, Appointment.class);
        toBeCreated.setPatient(patientsLookups.get(appointment.getPatientId()));
        toBeCreated.setPatientCase(patientCaseLookups.get(appointment.getPatientCaseId()));
        toBeCreated.setClinic(clinicsLookups.get(appointment.getClinicId()));
        toBeCreated.setAppointmentType(appointmentTypeLookups.get(appointment.getAppointmentTypeId()));
        toBeCreated.setAppointmentStatus(appointment.getId() == null ? AppointmentStatus.Created : appointment.getAppointmentStatus());
        return toBeCreated;
    }

    public void setAppointmentLookups(Map<Long, Clinic> clinicsLookups,
                                      Map<Long, Patient> patientsLookups,
                                      Map<Long, PatientCase> patientCaseLookups,
                                      Map<Long, AppointmentType> appointmentTypeLookups) {
        this.clinicsLookups = clinicsLookups;
        this.patientsLookups = patientsLookups;
        this.patientCaseLookups = patientCaseLookups;
        this.appointmentTypeLookups = appointmentTypeLookups;
    }
}
