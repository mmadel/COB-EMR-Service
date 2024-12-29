package com.cob.emr.usecases.appointment;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import com.cob.emr.repositories.calendar.CalendarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class PersistAppointmentUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AppointmentTypeRepository appointmentTypeRepository;
    @Autowired
    CalendarRepository calendarRepository;


    public void persist(List<AppointmentModel> models, Long calendarId) {
        List<Appointment> appointments = new ArrayList<>();
        models.stream().forEach(model -> {
            AppointmentType type = appointmentTypeRepository.findById(model.getAppointmentTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("appointment not found"));
            Appointment appointment = persist(model, false);
            appointment.setAppointmentType(type);
            appointments.add(appointment);
        });
        Iterable<Appointment> createdAppointments = appointmentRepository.saveAll(appointments);
        assignToCalendar(createdAppointments, calendarId);
    }

    public Appointment persist(AppointmentModel model, boolean save) {
        mapper.getConfiguration().setAmbiguityIgnored(true);
        Appointment toBeCreated = mapper.map(model, Appointment.class);
        fillAppointmentAssociation(toBeCreated, model);
        if (save)
            appointmentRepository.save(toBeCreated);
        return toBeCreated;
    }

    public void fillAppointmentAssociation(Appointment toBeFilled, AppointmentModel model) {
        Clinic clinic = new Clinic();
        clinic.setId(model.getClinicId());

        Patient patient = new Patient();
        patient.setId(model.getPatientId());

        PatientCase patientCase = new PatientCase();
        patientCase.setId(model.getPatientCaseId());

        CalendarEntity calendar = new CalendarEntity();
        calendar.setId(model.getCalendarId());

        toBeFilled.setClinic(clinic);
        toBeFilled.setPatient(patient);
        toBeFilled.setPatientCase(patientCase);
        if (model.getId() == null)
            toBeFilled.setAppointmentStatus(AppointmentStatus.Created);
        else
            toBeFilled.setAppointmentStatus(model.getAppointmentStatus());
    }

    private void assignToCalendar(Iterable<Appointment> createdAppointments, Long calendarId) {
        List<Appointment> list = StreamSupport.stream(createdAppointments.spliterator(), false)
                .collect(Collectors.toList());
        CalendarEntity calendar = calendarRepository.findById(calendarId)
                .orElseThrow(() -> new IllegalArgumentException("Calendar not found"));
        list.forEach(appointment -> {
            calendar.addAppointment(appointment);
        });
        calendarRepository.save(calendar);
    }
}
