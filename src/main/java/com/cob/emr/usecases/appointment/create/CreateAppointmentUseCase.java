package com.cob.emr.usecases.appointment.create;

import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.model.appointment.AppointmentRepetitionType;
import com.cob.emr.usecases.appointment.PersistAppointmentUseCase;
import com.cob.emr.usecases.appointment.create.creator.*;
import com.cob.emr.usecases.appointment.series.CreateAppointmentSeriesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CreateAppointmentUseCase {
    @Autowired
    PersistAppointmentUseCase persistAppointmentUseCase;
    @Autowired
    CreateAppointmentSeriesUseCase createAppointmentSeriesUseCase;

    AppointmentCreator appointmentCreator;

    @Transactional
    public List<AppointmentModel> execute(AppointmentModel model) {
        setAppointmentCreator(model);
        appointmentCreator.create(model);
        return persistAppointmentUseCase.persist(appointmentCreator.appointments, model);
    }

    private void setAppointmentCreator(AppointmentModel model) {
        if (model.getAppointmentRepetitionType() == null)
            appointmentCreator = new SingleAppointmentCreator();
        else
            switch (model.getAppointmentRepetitionType()) {
                case Single:
                    appointmentCreator = new SingleAppointmentCreator();
                    break;
                case Daily:
                    appointmentCreator = new DailyAppointmentCreator();
                    createAppointmentSeriesUseCase.create(model);
                    break;
                case Weekly:
                    appointmentCreator = new WeeklyAppointmentCreator();
                    createAppointmentSeriesUseCase.create(model);
                    break;
                case Monthly:
                    appointmentCreator = new MonthlyAppointmentCreator();
                    createAppointmentSeriesUseCase.create(model);
                    break;
                case Yearly:
                    appointmentCreator = new YearlyAppointmentCreator();
                    createAppointmentSeriesUseCase.create(model);
                    break;

            }
    }
}
