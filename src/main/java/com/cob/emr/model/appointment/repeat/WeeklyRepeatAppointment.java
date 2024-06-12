package com.cob.emr.model.appointment.repeat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WeeklyRepeatAppointment {
    Integer every;
    int[] days;
    Long start;
    Long end;
}
