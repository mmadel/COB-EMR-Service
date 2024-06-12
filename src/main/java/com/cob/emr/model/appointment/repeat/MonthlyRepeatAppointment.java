package com.cob.emr.model.appointment.repeat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonthlyRepeatAppointment {
    Integer every;
    Long start;
    Long end;
    int[] dayInMonth;
}
