package com.cob.emr.usecases.appointment.series;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.model.appointment.AppointmentTypeModel;
import com.cob.emr.model.appointment.series.AppointmentBlockSeries;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindBlockAppointmentSeriesUseCase {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    ModelMapper mapper;

    public AppointmentBlockSeries find(Long clinicId, Integer seriesId) {
        Appointment appointment = appointmentRepository.findAppointmentSeries(clinicId, seriesId)
                .stream().findFirst().get();
        AppointmentBlockSeries series = new AppointmentBlockSeries();

        series.setTitle(appointment.getTitle());
        series.setAppointmentType(mapper.map(appointment.getAppointmentType(), AppointmentTypeModel.class));
        series.setStartTime(appointment.getStartDate());
        series.setEndTime(appointment.getEndDate());
        series.setClinic(mapper.map(appointment.getClinic(), ClinicModel.class));
        series.setNote(appointment.getNote());
        return series;
    }
}
