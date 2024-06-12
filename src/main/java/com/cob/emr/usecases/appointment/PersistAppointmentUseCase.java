package com.cob.emr.usecases.appointment;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersistAppointmentUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    AppointmentRepository appointmentRepository;

    public void persist(List<AppointmentModel> models) {
        List<Appointment> appointments = new ArrayList<>();
        models.stream().forEach(model -> {
            Appointment appointment = persist(model, false);
            appointments.add(appointment);
        });
        appointmentRepository.saveAll(appointments);
    }

    public Appointment persist(AppointmentModel model, boolean save) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        Appointment toBeCreated = mapper.map(model, Appointment.class);
        fillAppointmentAssociation(toBeCreated, model);
        if (save)
            appointmentRepository.save(toBeCreated);
        return toBeCreated;
    }

    public void fillAppointmentAssociation(Appointment toBeFilled, AppointmentModel model) {
        Clinic clinic = new Clinic();
        clinic.setId(model.getClinicId());

        Patient patient = new Patient();
        patient.setId(model.getPatientId());

        PatientCase patientCase = new PatientCase();
        patientCase.setId(model.getPatientCaseId());

        toBeFilled.setClinic(clinic);
        toBeFilled.setPatient(patient);
        toBeFilled.setPatientCase(patientCase);
        if (model.getId() == null)
            toBeFilled.setAppointmentStatus(AppointmentStatus.Created);
        else
            toBeFilled.setAppointmentStatus(model.getAppointmentStatus());
    }
}
