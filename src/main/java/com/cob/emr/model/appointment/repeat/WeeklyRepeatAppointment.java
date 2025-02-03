package com.cob.emr.model.appointment.repeat;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Setter
@Getter
public class WeeklyRepeatAppointment {
    private Integer every;
    private Long startDate;
    private Long endDate;
    private int[] days;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeeklyRepeatAppointment that = (WeeklyRepeatAppointment) o;
        return every.equals(that.every) && startDate.equals(that.startDate) && endDate.equals(that.endDate) && Arrays.equals(days, that.days);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(every, startDate, endDate);
        result = 31 * result + Arrays.hashCode(days);
        return result;
    }
}
