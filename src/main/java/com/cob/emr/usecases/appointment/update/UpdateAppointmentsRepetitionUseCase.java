package com.cob.emr.usecases.appointment.update;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.model.appointment.RenderAppointment;
import com.cob.emr.model.appointment.RenderAppointmentContainer;
import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.appointment.AppointmentSeriesRepository;
import com.cob.emr.usecases.appointment.ConstructRenderAppointmentUseCase;
import com.cob.emr.usecases.appointment.create.CreateSchedulerAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class UpdateAppointmentsRepetitionUseCase {
    @Autowired
    private AppointmentSeriesRepository appointmentSeriesRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    ConstructRenderAppointmentUseCase constructRenderAppointmentUseCase;
    @Autowired
    @Qualifier("CreateSeriesSchedulerAppointment")
    private CreateSchedulerAppointment createSchedulerAppointment;

    @Transactional
    public RenderAppointmentContainer update(SchedulerAppointment schedulerAppointment) {
        List<RenderAppointment> toBeDeletedAppointments = getAppointmentsSeriesToBeDeleted(schedulerAppointment.getSeriesId());
        appointmentRepository.deleteBySeriesId(schedulerAppointment.getSeriesId());
        appointmentSeriesRepository.deleteBySeriesId(schedulerAppointment.getSeriesId());
        return RenderAppointmentContainer.builder()
                .addedRenderAppointment((List<RenderAppointment>) createSchedulerAppointment.create(schedulerAppointment))
                .deletedRenderAppointment(toBeDeletedAppointments)
                .build();
    }

    private List<RenderAppointment> getAppointmentsSeriesToBeDeleted(Integer seriesId) {
        List<Appointment> appointments = appointmentRepository.findBySeriesId(seriesId)
                .orElseThrow(() -> new IllegalArgumentException("no appointment series by series id " + seriesId));
        return constructRenderAppointmentUseCase.construct(appointments);
    }
}
