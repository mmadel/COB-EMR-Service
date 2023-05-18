package com.cob.emr.service.appointment.chart;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.model.response.AppointmentResponse;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentChartPreviousFinderService {
    @Autowired
    AppointmentRepository appointmentRepository;

    /* Appointments have been started */
    public AppointmentResponse findAllPreviousAppointments(Long patientId, Long clinicId,
                                                           Pageable pageable) {
        final long currentDate = (new Date().getTime());
        Page<List<Appointment>> page = appointmentRepository.findAllPreviousAppointments(patientId, clinicId, currentDate, pageable);
        return PaginationAppointmentUtil.getAppointmentPageContent(page);
    }

    /* Appointments have been started by case */
    public AppointmentResponse findPreviousAppointmentsByCase(Long patientId, Long clinicId, Long patientCaseId,
                                                                     Pageable pageable) {
        final long currentDate = (new Date().getTime());
        Page<List<Appointment>> page = appointmentRepository.findPreviousAppointmentsByCase(patientId, clinicId, patientCaseId, currentDate, pageable);
        return PaginationAppointmentUtil.getAppointmentPageContent(page);
    }
}
