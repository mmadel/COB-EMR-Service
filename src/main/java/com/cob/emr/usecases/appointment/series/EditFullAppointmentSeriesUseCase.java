package com.cob.emr.usecases.appointment.series;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.model.appointment.series.AppointmentFullSeries;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.usecases.appointment.util.BuildAppointments;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EditFullAppointmentSeriesUseCase {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    BuildAppointments buildAppointments;

    public List<AppointmentModel> edit(AppointmentFullSeries model, Integer seriesId) {
        List<Appointment> appointments =
                appointmentRepository.findAppointmentSeries(model.getClinic().getId(), seriesId);
        appointments.forEach(appointment -> {
            map(model, appointment);
        });
        appointmentRepository.saveAll(appointments);
        return buildAppointments.build(appointments);
    }

    private void map(AppointmentFullSeries model, Appointment appointment) {
        appointment.setAppointmentType(mapper.map(model.getAppointmentType(), AppointmentType.class));
        appointment.setNote(model.getNote());
        appointment.setPatientCase(mapper.map(model.getPatientCase(), PatientCase.class));

    }
}
