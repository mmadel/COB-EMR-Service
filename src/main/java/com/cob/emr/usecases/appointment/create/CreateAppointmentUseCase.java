package com.cob.emr.usecases.appointment.create;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.model.appointment.AppointmentRepetitionType;
import com.cob.emr.usecases.appointment.PersistAppointmentUseCase;
import com.cob.emr.usecases.appointment.create.creator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateAppointmentUseCase {
    @Autowired
    PersistAppointmentUseCase persistAppointmentUseCase;

    AppointmentCreator appointmentCreator;

    public void execute(AppointmentModel model) {
        setAppointmentCreator(model.getAppointmentRepetitionType());
        appointmentCreator.create(model);
        persistAppointmentUseCase.persist(appointmentCreator.appointments);
    }

    private void setAppointmentCreator(AppointmentRepetitionType appointmentRepetitionType) {
        switch (appointmentRepetitionType) {
            case Single:
                appointmentCreator = new SingleAppointmentCreator();
                break;
            case Daily:
                appointmentCreator = new DailyAppointmentCreator();
                break;
            case Weekly:
                appointmentCreator = new WeeklyAppointmentCreator();
                break;
            case Monthly:
                appointmentCreator = new MonthlyAppointmentCreator();
            case Yearly:
                appointmentCreator = new YearlyAppointmentCreator();
                break;

        }
    }
}
