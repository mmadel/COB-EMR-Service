package com.cob.emr.service.appointment.chart;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentChartFinderService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    ModelMapper mapper;

    /*
        Appointments that will start in the future
     */
    public PageImpl<AppointmentModel> findAllIncomingAppointments(Long patientId, Long clinicId,
                                                                  Pageable pageable) {
        final long currentDate = (new Date().getTime());
        Page<List<Appointment>> page = appointmentRepository.findAllIncomingAppointments(patientId, clinicId, currentDate, pageable);
        return getAppointmentPageContent(page);
    }
        /*
        Appointments that will start in the future by patient case
     */

    public PageImpl<AppointmentModel> findIncomingAppointmentsByCase(Long patientId, Long clinicId, Long patientCaseId,
                                                                     Pageable pageable) {
        final long currentDate = (new Date().getTime());
        Page<List<Appointment>> page = appointmentRepository.findIncomingAppointmentsByCase(patientId, clinicId, patientCaseId, currentDate, pageable);
        return getAppointmentPageContent(page);
    }

    private PageImpl<AppointmentModel> getAppointmentPageContent(Page<List<Appointment>> page) {
        List<AppointmentModel> result = new ArrayList<>();

        for (int i = 0; i < page.getContent().size(); i++) {
            result.add(mapper.map(page.getContent().get(i), AppointmentModel.class));
        }
        return new PageImpl<>(result, page.getPageable(), page.getTotalElements());
    }
}
