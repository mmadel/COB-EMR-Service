package com.cob.emr.model.appointment;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppointmentCancelNoShowReasonModel {
    private Long id;
    private String reason;
    private Long reasonDate;
    private String comment;
    private Long appointmentId;
}
