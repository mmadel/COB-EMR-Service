package com.cob.emr.model.appointment;

import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.enums.AppointmentStructure;
import com.cob.emr.model.appointment.repeat.AppointmentRepetitionConfiguration;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SchedulerAppointment {
    private Long id;
    private String title;
    private String note;
    private Long startDate;// start date combined with start time
    private Long endDate; // start date combined with start time
    private AppointmentStatus appointmentStatus;
    private Long clinicId;
    private Long calendarId;
    private Long appointmentTypeId;
    private AppointmentRepetitionConfiguration appointmentRepetitionConfiguration;
    private Long patientId;
    private Long patientCaseId;
    private String therapyUUID;
    private Integer seriesId;
    private Boolean isStatusChangeable;
    private AppointmentStructure appointmentStructure;
}
