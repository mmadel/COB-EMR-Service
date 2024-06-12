package com.cob.emr.usecases.appointment.create;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.model.appointment.AppointmentRepetitionType;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.usecases.appointment.create.creator.AppointmentCreator;
import com.cob.emr.usecases.appointment.create.creator.DailyAppointmentCreator;
import com.cob.emr.usecases.appointment.create.creator.SingleAppointmentCreator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateAppointmentUseCase {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    ModelMapper mapper;

    AppointmentCreator appointmentCreator;

    public void execute(AppointmentModel model) {
        setAppointmentCreator(model.getAppointmentRepetitionType());
        appointmentCreator.create(model);
        saveAppointment(model);
    }

    private void setAppointmentCreator(AppointmentRepetitionType appointmentRepetitionType) {
        switch (appointmentRepetitionType) {
            case Single:
                appointmentCreator = new SingleAppointmentCreator();
                break;
            case Daily:
                appointmentCreator = new DailyAppointmentCreator();
                break;
            case weekly:
                break;
            case Monthly:
                break;
            case Yearly:
                break;

        }
    }

    private void saveAppointment(AppointmentModel originalModel) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        List<Appointment>  appointments= appointmentCreator.appointments.stream()
                .map(model -> {
                    Appointment toBeCreated = mapper.map(model, Appointment.class);
                    fillAppointmentAssociation(toBeCreated, originalModel);
                    return toBeCreated;
                }).collect(Collectors.toList());
        appointmentRepository.saveAll(appointments);
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
