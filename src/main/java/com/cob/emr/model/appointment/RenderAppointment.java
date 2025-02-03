package com.cob.emr.model.appointment;

import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.enums.AppointmentStructure;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RenderAppointment {
    private Long id;
    private String title;
    private String note;
    private Long startDate;
    private Long endDate;
    private String appointmentTypeColor;
    private String appointmentFontTypeColor;
    private AppointmentStatus appointmentStatus;
    private String appointmentType;
    private AppointmentStructure appointmentStructure;
    private Long calendarId;
    private Long patientId;
    private Integer seriesId;
}
