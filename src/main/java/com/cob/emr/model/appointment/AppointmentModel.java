package com.cob.emr.model.appointment;


import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.enums.AppointmentStructure;
import com.cob.emr.model.appointment.repeat.AppointmentRepetitionConfiguration;
import com.cob.emr.model.clinic.ClinicModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppointmentModel {
    private Long id;
    private Long startDate;
    private Long endDate;
    private String title;
    private String note;
    private String therapyUUID;
    private AppointmentStatus appointmentStatus;
    private Long clinicId;
    private Long patientId;
    private Long calendarId;
    private Long patientCaseId;
    private Long appointmentTypeId;
    private String appointmentType;
    private String appointmentTypeColor;
    private String appointmentFontTypeColor;
    private AppointmentRepetitionConfiguration appointmentRepetitionConfiguration;
    private ClinicModel clinicModel;
    private AppointmentCancelNoShowReasonModel appointmentCancelNoShowReason;
    private AppointmentStructure appointmentStructure;
    private Integer seriesId;
}
