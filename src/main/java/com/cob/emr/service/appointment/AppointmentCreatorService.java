package com.cob.emr.service.appointment;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.appointment.AppointmentCancelNoShowReason;
import com.cob.emr.entity.appointment.AppointmentStatusHistory;
import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.repositories.appointment.AppointmentCancelNoShowReasonRepository;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.appointment.AppointmentStatusHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AppointmentCreatorService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AppointmentStatusHistoryRepository statusHistoryRepository;
    @Autowired
    AppointmentCancelNoShowReasonRepository appointmentCancelNoShowReasonRepository;
    @Autowired
    ModelMapper mapper;

    public Long createOrUpdate(AppointmentModel model) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        Appointment toBeCreated = mapper.map(model, Appointment.class);
        fillAppointmentAssociation(toBeCreated, model);
        Appointment createdAppointment = appointmentRepository.save(toBeCreated);
        //addAppointmentHistoryRecord(model,createdAppointment);
        if (model.getAppointmentCancelNoShowReason() != null) {
            AppointmentCancelNoShowReason appointmentCancelNoShowReason = mapper.map(model.getAppointmentCancelNoShowReason(), AppointmentCancelNoShowReason.class);
            appointmentCancelNoShowReason.setAppointment(createdAppointment);
            appointmentCancelNoShowReasonRepository.save(appointmentCancelNoShowReason);
        }
        return createdAppointment.getId();
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

    private void addAppointmentHistoryRecord(AppointmentModel model, Appointment createdAppointment) {
        AppointmentStatusHistory appointmentStatusHistory = new AppointmentStatusHistory();
        appointmentStatusHistory.setStatus(model.getAppointmentStatus());
        appointmentStatusHistory.setCreatedDate(new Date().getTime());
        appointmentStatusHistory.setAppointment(createdAppointment);
        statusHistoryRepository.save(appointmentStatusHistory);

    }

}
