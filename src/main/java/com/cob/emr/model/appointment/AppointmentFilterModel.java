package com.cob.emr.model.appointment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppointmentFilterModel {
    private String[] typeList;
    private String[] statusList;
    private String[] doctorList;
}
