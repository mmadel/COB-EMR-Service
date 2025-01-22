package com.cob.emr.model.appointment.repeat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class YearlyRepeatAppointment {
    Integer every;
    Long start;
    Long end;
    Long startDate;
    Long initDate;
}
