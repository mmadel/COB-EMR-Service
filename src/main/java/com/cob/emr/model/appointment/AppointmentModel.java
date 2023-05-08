package com.cob.emr.model.appointment;

import com.cob.emr.enums.AppointmentStatus;
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

    private AppointmentStatus appointmentStatus;

    private Long clinicId;

    private Long patientId;

    private Long patientCaseId;

    private Long appointmentTypeId;
}
