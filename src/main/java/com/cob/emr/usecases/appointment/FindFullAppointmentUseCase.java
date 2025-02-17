package com.cob.emr.usecases.appointment;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.appointment.series.AppointmentSeries;
import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.model.appointment.repeat.AppointmentRepetitionConfiguration;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.appointment.AppointmentSeriesRepository;
import com.cob.emr.usecases.appointment.series.mapper.AppointmentRepetitionConfigurationMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindFullAppointmentUseCase {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentSeriesRepository appointmentSeriesRepository;
    @Autowired
    ModelMapper mapper;

    public SchedulerAppointment find(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found "));
        SchedulerAppointment model = mapper.map(appointment, SchedulerAppointment.class);
        assignAppointmentRepetitionConfiguration(appointment, model);
        return model;
    }

    private void assignAppointmentRepetitionConfiguration(Appointment appointment, SchedulerAppointment model) {
        if (!(appointment.getSeriesId().equals(appointment.getId().intValue()))) {
            AppointmentSeries appointmentSeries = appointmentSeriesRepository.findBySeriesId(appointment.getSeriesId())
                    .orElseThrow(() -> new IllegalArgumentException("Appointment series not found for id " + appointment.getSeriesId()));
            AppointmentRepetitionConfiguration appointmentRepetitionConfiguration = new AppointmentRepetitionConfiguration();
            AppointmentRepetitionConfigurationMapper.map(appointmentRepetitionConfiguration, appointmentSeries);
            model.setAppointmentRepetitionConfiguration(appointmentRepetitionConfiguration);
        } else {
            model.setAppointmentRepetitionConfiguration(new AppointmentRepetitionConfiguration());
        }
    }
}
