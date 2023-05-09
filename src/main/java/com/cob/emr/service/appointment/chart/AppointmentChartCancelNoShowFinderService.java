package com.cob.emr.service.appointment.chart;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentChartCancelNoShowFinderService {
    @Autowired
    AppointmentRepository appointmentRepository;

    public PageImpl<AppointmentModel> findCancelAppointments(Long patientId, Long clinicId, Long patientCaseId, Pageable pageable) {
        final long currentDate = (new Date().getTime());
        Page<List<Appointment>> page = appointmentRepository.findAllCancelAppointments(patientId, clinicId, patientCaseId, pageable);
        return PaginationAppointmentUtil.getAppointmentPageContent(page);
    }

    public PageImpl<AppointmentModel> findNoShowAppointments(Long patientId, Long clinicId, Long patientCaseId, Pageable pageable) {
        final long currentDate = (new Date().getTime());
        Page<List<Appointment>> page = appointmentRepository.findAllNoShowAppointments(patientId, clinicId, patientCaseId, pageable);
        return PaginationAppointmentUtil.getAppointmentPageContent(page);
    }

    public PageImpl<AppointmentModel> findCancelNoShowAppointments(Long patientId, Long clinicId, Long patientCaseId, Pageable pageable) {
        final long currentDate = (new Date().getTime());
        Page<List<Appointment>> page = appointmentRepository.findCancelNoShowAppointments(patientId, clinicId, patientCaseId, pageable);
        return PaginationAppointmentUtil.getAppointmentPageContent(page);
    }
}
