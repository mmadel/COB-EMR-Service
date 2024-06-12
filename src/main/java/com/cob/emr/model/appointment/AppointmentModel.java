package com.cob.emr.model.appointment;


import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.model.appointment.repeat.AppointmentRepeat;
import com.cob.emr.model.patient.PatientModel;
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
    private Integer repeatId;
    private String therapyUUID;
    private AppointmentStatus appointmentStatus;
    private Long clinicId;
    private Long patientId;
    private PatientModel patient;
    private Long patientCaseId;
    private String appointmentType;
    private String appointmentTypeColor;
    private AppointmentRepeat appointmentRepeat;
    private AppointmentRepetitionType appointmentRepetitionType;
    private AppointmentCancelNoShowReasonModel appointmentCancelNoShowReason;
}
