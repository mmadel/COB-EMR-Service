package com.cob.emr.usecases.appointment.series;

import com.cob.emr.entity.appointment.series.AppointmentSeries;
import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.repositories.appointment.AppointmentSeriesRepository;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import com.cob.emr.usecases.appointment.series.mapper.AppointmentSeriesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CreateAppointmentSeriesUseCase {
    @Autowired
    AppointmentSeriesRepository appointmentSeriesRepository;
    @Autowired
    AppointmentTypeRepository appointmentTypeRepository;

    public void create(SchedulerAppointment appointment) {
        AppointmentSeries series = new AppointmentSeries();
        Random rand = new Random();
        Integer seriesId = rand.nextInt(1000000);
        series.setSeriesId(seriesId);
        appointment.setSeriesId(seriesId);
        AppointmentSeriesMapper.map(series, appointment.getAppointmentRepetitionConfiguration());
        series.setRepetitionType(appointment.getAppointmentRepetitionConfiguration().getAppointmentRepetitionType());
        appointmentSeriesRepository.save(series);
    }
}
