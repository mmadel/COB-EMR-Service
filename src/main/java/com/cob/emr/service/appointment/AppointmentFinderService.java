package com.cob.emr.service.appointment;

import com.cob.emr.Utils.PatientUtil;
import com.cob.emr.entity.appointment.Appointment;
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
                .map(appointment -> {
                    AppointmentModel appointmentModel = mapper.map(appointment, AppointmentModel.class);
                    String title = PatientUtil.constructPatientName(appointment.getPatient().getFirstName(),
                            appointment.getPatient().getMiddleName(), appointment.getPatient().getLastName())
                            + ":" + appointment.getPatientCase().getTitle();
                    appointmentModel.setTitle(title);
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
        String title = PatientUtil.constructPatientName(appointment.getPatient().getFirstName(),
                appointment.getPatient().getMiddleName(), appointment.getPatient().getLastName())
                + ":" + appointment.getPatientCase().getTitle();
        appointmentModel.setTitle(title);
        return appointmentModel;
    }
}
