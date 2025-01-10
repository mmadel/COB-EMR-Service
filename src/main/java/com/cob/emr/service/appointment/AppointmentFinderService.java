package com.cob.emr.service.appointment;

import com.cob.emr.Utils.PatientUtil;
import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.enums.AppointmentStructure;
import com.cob.emr.model.appointment.AppointmentFilterModel;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentFinderService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AppointmentTypeRepository appointmentTypeRepository;
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
    public AppointmentModel find(Long appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        AppointmentModel appointmentModel = mapper.map(appointment, AppointmentModel.class);
        appointmentModel.setAppointmentStructure(appointment.getPatient()==null?AppointmentStructure.Block:AppointmentStructure.Full);
        return appointmentModel;
    }
}
