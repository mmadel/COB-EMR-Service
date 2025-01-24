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
        //Type
        series.setRepetitionType(appointment.getAppointmentRepetitionType());
        Random rand = new Random();
        Integer seriesId = rand.nextInt(1000000);
        series.setSeriesId(seriesId);
        appointment.setSeriesId(seriesId);
        appointmentSeriesRepository.save(series);
    }
}
