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
public class AppointmentChartIncomingFinderService {
    @Autowired
    AppointmentRepository appointmentRepository;

    public AppointmentResponse findAllIncomingAppointments(Long patientId, Long clinicId,
                                                           Pageable pageable) {
        final long currentDate = (new Date().getTime());
        Page<List<Appointment>> page = appointmentRepository.findAllIncomingAppointments(patientId, clinicId, currentDate, pageable);
        return PaginationAppointmentUtil.getAppointmentPageContent(page);
    }
    /* Appointments that will start in the future by patient case */

    public AppointmentResponse findIncomingAppointmentsByCase(Long patientId, Long clinicId, Long patientCaseId,
                                                                     Pageable pageable) {
        final long currentDate = (new Date().getTime());
        Page<List<Appointment>> page = appointmentRepository.findIncomingAppointmentsByCase(patientId, clinicId, patientCaseId, currentDate, pageable);
        return PaginationAppointmentUtil.getAppointmentPageContent(page);
    }
}
