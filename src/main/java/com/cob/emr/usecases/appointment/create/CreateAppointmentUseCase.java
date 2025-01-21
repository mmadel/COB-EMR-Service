package com.cob.emr.usecases.appointment.create;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.model.appointment.AppointmentRepetitionType;
import com.cob.emr.usecases.appointment.PersistAppointmentUseCase;
import com.cob.emr.usecases.appointment.create.creator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CreateAppointmentUseCase {
    @Autowired
    PersistAppointmentUseCase persistAppointmentUseCase;

    AppointmentCreator appointmentCreator;

    @Transactional
    public List<AppointmentModel> execute(AppointmentModel model) {
        setAppointmentCreator(model.getAppointmentRepetitionType());
        appointmentCreator.create(model);
        return persistAppointmentUseCase.persist(appointmentCreator.appointments, model.getCalendarId());
    }

    private void setAppointmentCreator(AppointmentRepetitionType appointmentRepetitionType) {
        if (appointmentRepetitionType == null)
            appointmentCreator = new SingleAppointmentCreator();
        else
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
                    break;
                case Yearly:
                    appointmentCreator = new YearlyAppointmentCreator();
                    break;

            }
    }
}
