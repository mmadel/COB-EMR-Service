package com.cob.emr.model.appointment.repeat;

import com.cob.emr.model.appointment.AppointmentRepetitionType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class AppointmentRepetitionConfiguration {
    private Boolean ignore;
    private AppointmentRepetitionType appointmentRepetitionType;
    private DailyRepeatAppointment daily;
    private WeeklyRepeatAppointment weekly;
    private MonthlyRepeatAppointment monthly;
    private YearlyRepeatAppointment yearly;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentRepetitionConfiguration that = (AppointmentRepetitionConfiguration) o;
        return appointmentRepetitionType == that.appointmentRepetitionType && daily.equals(that.daily) && weekly.equals(that.weekly) && monthly.equals(that.monthly) && yearly.equals(that.yearly);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentRepetitionType, daily, weekly, monthly, yearly);
    }
}
