package com.cob.emr.usecases.appointment.update;


import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.model.appointment.RenderAppointment;
import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.usecases.appointment.ConstructRenderAppointmentUseCase;
import com.cob.emr.usecases.appointment.create.CreateAppointmentEntityUseCase;
import com.cob.emr.usecases.appointment.create.FetchAppointmentsSchedulerLookupUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateSingleSchedulerAppointmentUseCase {
    @Autowired
    FetchAppointmentsSchedulerLookupUseCase schedulerAppointmentsLookup;

    @Autowired
    CreateAppointmentEntityUseCase createAppointmentEntityUseCase;
    @Autowired
    ConstructRenderAppointmentUseCase constructRenderAppointmentUseCase;
    @Autowired
    AppointmentRepository appointmentRepository;

    public RenderAppointment update(SchedulerAppointment appointment) {
        fetchAppointmentLookups(appointment);
        Appointment toBecreatedAppointment = createAppointmentEntityUseCase.create(appointment);
        deAttachFromSeries(appointment.getId(),appointment.getSeriesId(),toBecreatedAppointment);
        appointmentRepository.save(toBecreatedAppointment);
        return constructRenderAppointmentUseCase.construct(toBecreatedAppointment);
    }

    private void fetchAppointmentLookups(SchedulerAppointment appointment) {
        List<SchedulerAppointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        schedulerAppointmentsLookup.fetch(appointments);
        createAppointmentEntityUseCase.setAppointmentLookups(schedulerAppointmentsLookup.clinicsLookups
                , schedulerAppointmentsLookup.patientsLookups
                , schedulerAppointmentsLookup.patientCaseLookups
                , schedulerAppointmentsLookup.appointmentTypeLookups);
    }
    private void deAttachFromSeries(Long appointmentId , Integer seriesId , Appointment appointment){
        if(appointmentId != seriesId.longValue()){
            appointment.setSeriesId(appointmentId.intValue());
        }
    }
}
