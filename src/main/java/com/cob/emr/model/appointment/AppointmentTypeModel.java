package com.cob.emr.model.appointment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppointmentTypeModel {
    private Long id;
    private String name;
    private String color;
    private Long duration;

    private Long clinicId;
}
