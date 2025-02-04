package com.cob.emr.usecases.appointment.update;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.appointment.series.AppointmentSeries;
import com.cob.emr.model.appointment.RenderAppointment;
import com.cob.emr.model.appointment.RenderAppointmentContainer;
import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.model.appointment.repeat.AppointmentRepetitionConfiguration;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.appointment.AppointmentSeriesRepository;
import com.cob.emr.usecases.appointment.ConstructRenderAppointmentUseCase;
import com.cob.emr.usecases.appointment.MapAppointmentSeriesToRepetitionConfiguration;
import com.cob.emr.usecases.appointment.MapAppointmentsSeriesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateSeriesSchedulerAppointmentUseCase {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    UpdateSingleSchedulerAppointmentUseCase updateSingleSchedulerAppointmentUseCase;
    @Autowired
    MapAppointmentsSeriesUseCase mapAppointmentsSeriesUseCase;
    @Autowired
    ConstructRenderAppointmentUseCase constructRenderAppointmentUseCase;
    @Autowired
    AppointmentSeriesRepository appointmentSeriesRepository;
    @Autowired
    UpdateAppointmentsRepetitionUseCase updateAppointmentsRepetitionUseCase;

    public RenderAppointmentContainer update(SchedulerAppointment schedulerAppointment) {
        AppointmentSeries appointmentSeries = appointmentSeriesRepository.findBySeriesId(schedulerAppointment.getSeriesId()).get();

        if (schedulerAppointment.getAppointmentRepetitionConfiguration().getIgnore() != null
                && schedulerAppointment.getAppointmentRepetitionConfiguration().getIgnore()) {
            return RenderAppointmentContainer.builder()
                    .addedRenderAppointment(updateSeriesAppointments(schedulerAppointment))
                    .deletedRenderAppointment(null)
                    .build();
        }
        AppointmentRepetitionConfiguration appointmentRepetitionConfiguration = MapAppointmentSeriesToRepetitionConfiguration.map(appointmentSeries);
        boolean isSameRepetition = checkRepetitionEquality(appointmentRepetitionConfiguration
                , schedulerAppointment.getAppointmentRepetitionConfiguration());
        if (isSameRepetition)
            return RenderAppointmentContainer.builder()
                    .addedRenderAppointment(updateSeriesAppointments(schedulerAppointment))
                    .deletedRenderAppointment(null)
                    .build();
        else
            return updateAppointmentsRepetitionUseCase.update(schedulerAppointment);
    }

    private List<RenderAppointment> updateSeriesAppointments(SchedulerAppointment schedulerAppointment) {
        List<RenderAppointment> renderAppointments = new ArrayList<>();
        List<Appointment> seriesAppointments = appointmentRepository.findBySeriesId(schedulerAppointment.getSeriesId())
                .orElseThrow(() -> new IllegalArgumentException("no appointments have been found with series id " + schedulerAppointment.getSeriesId()));
        Boolean isUpdate = mapAppointmentsSeriesUseCase.map(seriesAppointments, schedulerAppointment);
        if (isUpdate)
            appointmentRepository.saveAll(seriesAppointments);
        return constructRenderAppointmentUseCase.construct(seriesAppointments);
    }

    private boolean checkRepetitionEquality(AppointmentRepetitionConfiguration original, AppointmentRepetitionConfiguration modified) {
        return original.hashCode() == modified.hashCode();
    }
}
