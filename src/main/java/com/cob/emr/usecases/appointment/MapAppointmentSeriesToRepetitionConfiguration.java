package com.cob.emr.usecases.appointment;

import com.cob.emr.entity.appointment.series.AppointmentSeries;
import com.cob.emr.model.appointment.AppointmentRepetitionType;
import com.cob.emr.model.appointment.repeat.*;
import com.cob.emr.usecases.appointment.series.converter.MonthlyExtraDataConverter;
import com.cob.emr.usecases.appointment.series.converter.WeeklyExtraDataConverter;

public class MapAppointmentSeriesToRepetitionConfiguration {
    public static AppointmentRepetitionConfiguration map(AppointmentSeries appointmentSeries) {
        switch (appointmentSeries.getRepetitionType()) {
            case Daily:
                return setDailyConfiguration(appointmentSeries);
            case Weekly:
                return setWeeklyConfiguration(appointmentSeries);
            case Monthly:
                return setMonthlyConfiguration(appointmentSeries);
            case Yearly:
                return setYearlyConfiguration(appointmentSeries);

            default:
                throw new IllegalArgumentException("Appointment repetition type not found");
        }
    }

    private static AppointmentRepetitionConfiguration setDailyConfiguration(AppointmentSeries appointmentSeries) {
        AppointmentRepetitionConfiguration dailyConfiguration = new AppointmentRepetitionConfiguration();
        dailyConfiguration.setAppointmentRepetitionType(AppointmentRepetitionType.Daily);
        DailyRepeatAppointment daily = new DailyRepeatAppointment();
        daily.setStartDate(appointmentSeries.getStartDate());
        daily.setEndDate(appointmentSeries.getEndDate());
        daily.setEvery(appointmentSeries.getStep());
        dailyConfiguration.setDaily(daily);
        return dailyConfiguration;

    }

    private static AppointmentRepetitionConfiguration setWeeklyConfiguration(AppointmentSeries appointmentSeries) {
        AppointmentRepetitionConfiguration weeklyConfiguration = new AppointmentRepetitionConfiguration();
        weeklyConfiguration.setAppointmentRepetitionType(AppointmentRepetitionType.Weekly);
        WeeklyRepeatAppointment weekly = new WeeklyRepeatAppointment();
        weekly.setStartDate(appointmentSeries.getStartDate());
        weekly.setEndDate(appointmentSeries.getEndDate());
        weekly.setEvery(appointmentSeries.getStep());
        weekly.setDays(WeeklyExtraDataConverter.convert(appointmentSeries.getExtra()));
        weeklyConfiguration.setWeekly(weekly);
        return weeklyConfiguration;
    }

    private static AppointmentRepetitionConfiguration setMonthlyConfiguration(AppointmentSeries appointmentSeries) {
        AppointmentRepetitionConfiguration monthlyConfiguration = new AppointmentRepetitionConfiguration();
        monthlyConfiguration.setAppointmentRepetitionType(AppointmentRepetitionType.Monthly);
        MonthlyRepeatAppointment monthly = new MonthlyRepeatAppointment();
        monthly.setStartDate(appointmentSeries.getStartDate());
        monthly.setEndDate(appointmentSeries.getEndDate());
        monthly.setEvery(appointmentSeries.getStep());
        monthly.setDays(MonthlyExtraDataConverter.convert(appointmentSeries.getExtra()));
        monthlyConfiguration.setMonthly(monthly);
        return monthlyConfiguration;
    }

    private static AppointmentRepetitionConfiguration setYearlyConfiguration(AppointmentSeries appointmentSeries) {
        AppointmentRepetitionConfiguration yearlyConfiguration = new AppointmentRepetitionConfiguration();
        yearlyConfiguration.setAppointmentRepetitionType(AppointmentRepetitionType.Yearly);
        YearlyRepeatAppointment yearly = new YearlyRepeatAppointment();
        yearly.setStartDate(appointmentSeries.getStartDate());
        yearly.setEndDate(appointmentSeries.getEndDate());
        yearly.setEvery(appointmentSeries.getStep());
        yearlyConfiguration.setYearly(yearly);
        return yearlyConfiguration;
    }
}
