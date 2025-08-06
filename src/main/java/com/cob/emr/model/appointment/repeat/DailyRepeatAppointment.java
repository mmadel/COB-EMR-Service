package com.cob.emr.model.appointment.repeat;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
public class DailyRepeatAppointment  {
    private Integer every;
    private Long startDate;
    private Long endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyRepeatAppointment that = (DailyRepeatAppointment) o;
        return every.equals(that.every) && startDate.equals(that.startDate) && endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(every, startDate, endDate);
    }
}
