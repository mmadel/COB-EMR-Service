package com.cob.emr.usecases.appointment.series;

import com.cob.emr.entity.appointment.AppointmentSeries;
import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.repositories.appointment.AppointmentSeriesRepository;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CreateAppointmentSeriesUseCase {
    @Autowired
    AppointmentSeriesRepository appointmentSeriesRepository;
    @Autowired
    AppointmentTypeRepository appointmentTypeRepository;

    public void create(AppointmentModel appointment) {
        AppointmentSeries series = new AppointmentSeries();
        series.setTitle(appointment.getTitle());
        series.setNote(appointment.getNote());
        series.setTherapyUUID(appointment.getTherapyUUID());
        //Patient
        if (appointment.getPatientId() != null) {
            Patient patient = new Patient();
            patient.setId(appointment.getPatientId());
            series.setPatient(patient);
        }
        //clinic
        Clinic clinic = new Clinic();
        clinic.setId(appointment.getClinicId());
        series.setClinic(clinic);

        //Case
        if (appointment.getPatientCaseId() != null) {
            PatientCase patientCase = new PatientCase();
            patientCase.setId(appointment.getPatientCaseId());
            series.setPatientCase(patientCase);
        }
        //Type
        series.setRepetitionType(appointment.getAppointmentRepetitionType());

        //Appointment Type
        AppointmentType type = appointmentTypeRepository.findById(appointment.getAppointmentTypeId())
                .orElseThrow(() -> new IllegalArgumentException("appointment not found"));
        series.setAppointmentType(type);
        Random rand = new Random();
        Integer seriesId = rand.nextInt(1000000);
        series.setSeriesId(seriesId);
        appointment.setSeriesId(seriesId);
        appointmentSeriesRepository.save(series);
    }
}
