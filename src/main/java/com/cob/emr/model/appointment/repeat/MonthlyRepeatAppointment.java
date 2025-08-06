package com.cob.emr.model.appointment.repeat;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
public class MonthlyRepeatAppointment  {
    private Integer every;
    private Long startDate;
    private Long endDate;
    private MonthDay days;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthlyRepeatAppointment that = (MonthlyRepeatAppointment) o;
        return every.equals(that.every) && startDate.equals(that.startDate) && endDate.equals(that.endDate) && days.equals(that.days);
    }

    @Override
    public int hashCode() {
        return Objects.hash(every, startDate, endDate, days);
    }
}
