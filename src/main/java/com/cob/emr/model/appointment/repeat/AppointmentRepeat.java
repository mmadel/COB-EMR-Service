package com.cob.emr.model.appointment.repeat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppointmentRepeat {
    String type;
    DailyRepeatAppointment daily;
    WeeklyRepeatAppointment weekly;
    MonthlyRepeatAppointment monthly;
}
