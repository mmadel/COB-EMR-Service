package com.cob.emr.usecases.appointment.series.mapper;

import com.cob.emr.entity.appointment.series.AppointmentSeries;
import com.cob.emr.model.appointment.repeat.*;
import com.cob.emr.usecases.appointment.series.converter.MonthlyExtraDataConverter;
import com.cob.emr.usecases.appointment.series.converter.WeeklyExtraDataConverter;

public class AppointmentRepetitionConfigurationMapper {
    public static void map(AppointmentRepetitionConfiguration appointmentRepetitionConfiguration, AppointmentSeries series) {
        appointmentRepetitionConfiguration.setAppointmentRepetitionType(series.getRepetitionType());
        switch (series.getRepetitionType()) {
            case Daily:
                DailyRepeatAppointment dailyRepeatAppointment = new DailyRepeatAppointment();
                dailyRepeatAppointment.setEvery(series.getStep());
                dailyRepeatAppointment.setStartDate(series.getStartDate());
                dailyRepeatAppointment.setEndDate(series.getEndDate());
                appointmentRepetitionConfiguration.setDaily(dailyRepeatAppointment);
                break;
            case Weekly:
                WeeklyRepeatAppointment weeklyRepeatAppointment = new WeeklyRepeatAppointment();
                weeklyRepeatAppointment.setEvery(series.getStep());
                weeklyRepeatAppointment.setStartDate(series.getStartDate());
                weeklyRepeatAppointment.setEndDate(series.getEndDate());
                weeklyRepeatAppointment.setDays(WeeklyExtraDataConverter.convert(series.getExtra()));
                appointmentRepetitionConfiguration.setWeekly(weeklyRepeatAppointment);
                break;
            case Monthly:
                MonthlyRepeatAppointment monthlyRepeatAppointment = new MonthlyRepeatAppointment();
                monthlyRepeatAppointment.setEvery(series.getStep());
                monthlyRepeatAppointment.setStartDate(series.getStartDate());
                monthlyRepeatAppointment.setEndDate(series.getEndDate());
                monthlyRepeatAppointment.setDays(MonthlyExtraDataConverter.convert(series.getExtra()));
                appointmentRepetitionConfiguration.setMonthly(monthlyRepeatAppointment);
                break;
            case Yearly:
                YearlyRepeatAppointment yearlyRepeatAppointment = new YearlyRepeatAppointment();
                yearlyRepeatAppointment.setEvery(series.getStep());
                yearlyRepeatAppointment.setStartDate(series.getStartDate());
                yearlyRepeatAppointment.setEndDate(series.getEndDate());
                appointmentRepetitionConfiguration.setYearly(yearlyRepeatAppointment);
                break;
        }
    }
}
