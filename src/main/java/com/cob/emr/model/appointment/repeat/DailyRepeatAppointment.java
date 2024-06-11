package com.cob.emr.model.appointment.repeat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DailyRepeatAppointment {
    Long every;
    Long start;
    Long end;
}
