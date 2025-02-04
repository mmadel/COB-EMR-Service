package com.cob.emr.service.appointment;

import com.cob.emr.Utils.PatientUtil;
import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.appointment.series.AppointmentSeries;
import com.cob.emr.enums.AppointmentStructure;
import com.cob.emr.model.appointment.AppointmentFilterModel;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.model.appointment.SchedulerAppointment;
import com.cob.emr.model.appointment.repeat.AppointmentRepetitionConfiguration;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.appointment.AppointmentSeriesRepository;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import com.cob.emr.usecases.appointment.series.mapper.AppointmentRepetitionConfigurationMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentFinderService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AppointmentTypeRepository appointmentTypeRepository;
    @Autowired
    AppointmentSeriesRepository appointmentSeriesRepository;
    @Autowired
    ModelMapper mapper;

    public List<AppointmentModel> find(Long startDate, Long endDate, Long clinicId,Long calendarId) {
        return appointmentRepository.findAppointmentByDateRange(startDate, endDate,calendarId)
                .orElseThrow(() -> new IllegalArgumentException("Error Find Appointment" +
                        "startDate: " + startDate +
                        "endDate: " + endDate +
                        "calendarId " + calendarId))
                .stream()
                .map(appointment -> {
                    AppointmentModel appointmentModel = mapper.map(appointment, AppointmentModel.class);
                    appointmentModel.setAppointmentType(appointment.getAppointmentType().getName());
                    appointmentModel.setAppointmentTypeColor(appointment.getAppointmentType().getColor());
                    appointmentModel.setAppointmentFontTypeColor(appointment.getAppointmentType().getFontColor());
                    appointmentModel.setAppointmentStructure(appointment.getPatient() ==null? AppointmentStructure.Block:AppointmentStructure.Full);
                    return appointmentModel;
                })
                .collect(Collectors.toList());
    }

    public List<AppointmentModel> filter(Long startDate, Long endDate, Long clinicId, AppointmentFilterModel filterModel) {
        /// TODO: 5/9/2023 implements filter appointment by filter inputs and start,end and clinic
        return null;
    }
    public SchedulerAppointment find(Long appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        SchedulerAppointment model = mapper.map(appointment, SchedulerAppointment.class);
        model.setAppointmentStructure(appointment.getPatient()==null?AppointmentStructure.Block:AppointmentStructure.Full);
        model.setIsStatusChangeable(setStatusChangeable(appointment.getStartDate()));
        assignAppointmentRepetitionConfiguration(appointment, model);
        return model;
    }
    private void assignAppointmentRepetitionConfiguration(Appointment appointment, SchedulerAppointment model) {
        if (appointment.getSeriesId() != null &&(!(appointment.getSeriesId().equals(appointment.getId().intValue())))) {
            AppointmentSeries appointmentSeries = appointmentSeriesRepository.findBySeriesId(appointment.getSeriesId())
                    .orElseThrow(() -> new IllegalArgumentException("Appointment series not found for id " + appointment.getSeriesId()));
            AppointmentRepetitionConfiguration appointmentRepetitionConfiguration = new AppointmentRepetitionConfiguration();
            AppointmentRepetitionConfigurationMapper.map(appointmentRepetitionConfiguration, appointmentSeries);
            model.setAppointmentRepetitionConfiguration(appointmentRepetitionConfiguration);
        } else {
            model.setAppointmentRepetitionConfiguration(new AppointmentRepetitionConfiguration());
        }
    }
    private Boolean setStatusChangeable(Long startDate){
        Instant appointmentTime = Instant.ofEpochMilli(startDate);
        Instant now = Instant.now();
        // Calculate the time difference in minutes
        long minutesDifference = Duration.between(now, appointmentTime).toMinutes();
        // If the appointment is more than 15 minutes in the future, return false
        if (minutesDifference > 15) {
            return false;
        }
        // If the appointment is within 15 minutes in the future or past, return true
        return true;
    }
}
