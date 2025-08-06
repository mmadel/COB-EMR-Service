package com.cob.emr.usecases.appointment;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.enums.AppointmentStructure;
import com.cob.emr.model.appointment.RenderAppointment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ConstructRenderAppointmentUseCase {

    public List<RenderAppointment> construct(Iterable<Appointment> createdAppointments) {
        List<RenderAppointment> renderAppointments = new ArrayList<>();
        List<Appointment> list = StreamSupport.stream(createdAppointments.spliterator(), false)
                .collect(Collectors.toList());
        list.forEach(appointment -> {
            renderAppointments.add(construct(appointment));
        });
        return renderAppointments;
    }

    public RenderAppointment construct(Appointment appointment) {
        return RenderAppointment.builder()
                .id(appointment.getId())
                .title(appointment.getTitle())
                .note(appointment.getNote())
                .startDate(appointment.getStartDate())
                .endDate(appointment.getEndDate())
                .appointmentStatus(appointment.getAppointmentStatus())
                .appointmentStructure(appointment.getPatient() == null ? AppointmentStructure.Block : AppointmentStructure.Full)
                .seriesId(appointment.getSeriesId())
                .patientId(appointment.getPatient() != null ? appointment.getPatient().getId() : null)
                .calendarId(appointment.getCalendar().getId())
                .appointmentType(appointment.getAppointmentType().getName())
                .appointmentTypeColor(appointment.getAppointmentType().getColor())
                .appointmentFontTypeColor(appointment.getAppointmentType().getFontColor())
                .build();
    }
}
