package com.cob.emr.service.appointment;

import com.cob.emr.model.appointment.AppointmentFilterModel;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.repositories.appointment.AppointmentRepository;
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
    ModelMapper mapper;

    public List<AppointmentModel> find(Long startDate, Long endDate, Long clinicId) {
        return appointmentRepository.findAppointmentByDateRange(startDate, endDate, clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Error Find Appointment" +
                        "startDate: " + startDate +
                        "endDate: " + startDate +
                        "Clinic Id " + startDate))
                .stream()
                .map(appointment -> mapper.map(appointment, AppointmentModel.class))
                .collect(Collectors.toList());
    }

    public List<AppointmentModel> filter(Long startDate, Long endDate, Long clinicId, AppointmentFilterModel filterModel) {

        return null;
    }
}
