package com.cob.emr.usecases.appointment.series.mapper;

import com.cob.emr.entity.appointment.series.AppointmentSeries;
import com.cob.emr.model.appointment.repeat.AppointmentRepetitionConfiguration;
import com.cob.emr.usecases.appointment.series.converter.MonthlyExtraDataConverter;
import com.cob.emr.usecases.appointment.series.converter.WeeklyExtraDataConverter;

public class AppointmentSeriesMapper {
    public static void map(AppointmentSeries series, AppointmentRepetitionConfiguration appointmentRepetitionConfiguration) {
        switch (appointmentRepetitionConfiguration.getAppointmentRepetitionType()) {
            case Daily:
                setSeriesInfo(series, appointmentRepetitionConfiguration.getDaily().getEvery()
                        , appointmentRepetitionConfiguration.getDaily().getStartDate(),
                        appointmentRepetitionConfiguration.getDaily().getEndDate());
                series.setEndDate(appointmentRepetitionConfiguration.getDaily().getEndDate());
                break;
            case Weekly:
                setSeriesInfo(series, appointmentRepetitionConfiguration.getWeekly().getEvery()
                        , appointmentRepetitionConfiguration.getWeekly().getStartDate(),
                        appointmentRepetitionConfiguration.getWeekly().getEndDate());
                series.setExtra(WeeklyExtraDataConverter.convert(appointmentRepetitionConfiguration.getWeekly().getDays()));
                break;
            case Monthly:
                setSeriesInfo(series, appointmentRepetitionConfiguration.getMonthly().getEvery()
                        , appointmentRepetitionConfiguration.getMonthly().getStartDate(),
                        appointmentRepetitionConfiguration.getMonthly().getEndDate());
                series.setExtra(MonthlyExtraDataConverter.convert(appointmentRepetitionConfiguration.getMonthly().getDays()));
                break;
            case Yearly:
                setSeriesInfo(series, appointmentRepetitionConfiguration.getYearly().getEvery()
                        , appointmentRepetitionConfiguration.getYearly().getStartDate(),
                        appointmentRepetitionConfiguration.getYearly().getEndDate());
                break;
        }
    }
    private static void setSeriesInfo(AppointmentSeries appointmentSeries, Integer step, Long start, Long end) {
        appointmentSeries.setStep(step);
        appointmentSeries.setStartDate(start);
        appointmentSeries.setEndDate(end);
    }
}
