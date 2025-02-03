package com.cob.emr.usecases.appointment.create.repetition;


import com.cob.emr.Utils.BeanFactory;
import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.usecases.appointment.series.CreateAppointmentSeriesUseCase;

public class AppointmentRepetitionFactory {
    static CreateAppointmentSeriesUseCase createAppointmentSeriesUseCase = BeanFactory.getBean(CreateAppointmentSeriesUseCase.class);

    public static AppointmentRepetition getInstance(SchedulerAppointment appointment) {
        switch (appointment.getAppointmentRepetitionConfiguration().getAppointmentRepetitionType()) {
            case Daily:
                createAppointmentSeriesUseCase.create(appointment);
                return new DailyAppointmentRepetition();
            case Weekly:
                createAppointmentSeriesUseCase.create(appointment);
                return new WeeklyAppointmentRepetition();
            case Monthly:
                createAppointmentSeriesUseCase.create(appointment);
                return new MonthlyAppointmentRepetition();
            case Yearly:
                createAppointmentSeriesUseCase.create(appointment);
                return new YearlyAppointmentRepetition();
            default:
                throw new IllegalArgumentException("Invalid appointment repetition type: " + appointment
                        .getAppointmentRepetitionConfiguration().getAppointmentRepetitionType());
        }

    }
}
