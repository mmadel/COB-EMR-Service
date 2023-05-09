package com.cob.emr.service.appointment.history;

import com.cob.emr.model.appointment.AppointmentStatusHistoryModel;
import com.cob.emr.repositories.appointment.AppointmentStatusHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentHistoryFinderService {
    @Autowired
    AppointmentStatusHistoryRepository statusHistoryRepository;
    @Autowired
    ModelMapper mapper;

    public List<AppointmentStatusHistoryModel> find(Long appointmentId) {
        return statusHistoryRepository.find(appointmentId).orElseThrow(
                        () -> new IllegalArgumentException("find Appointment Status History"))
                .stream()
                .map(appointmentStatusHistory -> mapper.map(appointmentStatusHistory, AppointmentStatusHistoryModel.class))
                .collect(Collectors.toList());
    }
}
