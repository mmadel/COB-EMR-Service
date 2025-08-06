package com.cob.emr.model.appointment.series;

import com.cob.emr.model.appointment.AppointmentTypeModel;
import com.cob.emr.model.clinic.ClinicModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppointmentBlockSeries {
    private String title;
    private Long startTime;
    private Long endTime;
    private ClinicModel clinic;
    private AppointmentTypeModel appointmentType;
    private String note;

}
