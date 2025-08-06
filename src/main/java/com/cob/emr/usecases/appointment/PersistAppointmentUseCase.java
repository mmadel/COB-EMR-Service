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
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.usecases.appointment.util.BuildAppointments;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    BuildAppointments buildAppointments;

    public List<AppointmentModel> persist(List<AppointmentModel> models, AppointmentModel model) {
        Iterable<Appointment> createdAppointments;
        if (models.size() > 1)
            createdAppointments = createRepeatedAppointment(models, model.getSeriesId());
        else
            createdAppointments = createSingleAppointment(models
                    .stream()
                    .findFirst().get());
        assignToCalendar(createdAppointments, model.getCalendarId());
        return buildAppointments.build(createdAppointments);
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
        if (model.getPatientId() != null) {
            Patient patient = new Patient();
            patient.setId(model.getPatientId());
            toBeFilled.setPatient(patient);
        }

        if (model.getPatientCaseId() != null) {
            PatientCase patientCase = new PatientCase();
            patientCase.setId(model.getPatientCaseId());
            toBeFilled.setPatientCase(patientCase);
        }

        CalendarEntity calendar = new CalendarEntity();
        calendar.setId(model.getCalendarId());
        if (model.getId() == null)
            toBeFilled.setAppointmentStatus(AppointmentStatus.Created);
        else
            toBeFilled.setAppointmentStatus(model.getAppointmentStatus());
    }

    private Iterable<Appointment> createSingleAppointment(AppointmentModel model) {
        List<Appointment> appointments = new ArrayList<>();
        AppointmentType type = appointmentTypeRepository.findById(model.getAppointmentTypeId())
                .orElseThrow(() -> new IllegalArgumentException("appointment not found"));
        Clinic clinic = clinicRepository.findById(model.getClinicId())
                .orElseThrow(() -> new IllegalArgumentException("clinic not found"));
        Appointment appointment = persist(model, false);
        appointment.setAppointmentType(type);
        appointment.setClinic(clinic);
        appointments.add(appointment);
        Iterable<Appointment> appointmentIterable = appointmentRepository.saveAll(appointments);
        Appointment createdAppointment = StreamSupport.stream(appointmentIterable.spliterator(), false)
                .collect(Collectors.toList())
                .stream().findFirst()
                .get();
        createdAppointment.setSeriesId(createdAppointment.getId().intValue());
        appointmentRepository.save(createdAppointment);
        return appointmentIterable;
    }

    private Iterable<Appointment> createRepeatedAppointment(List<AppointmentModel> models, Integer seriesId) {
        List<Appointment> appointments = new ArrayList<>();
        models.stream().forEach(model -> {
            AppointmentType type = appointmentTypeRepository.findById(model.getAppointmentTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("appointment not found"));
            Clinic clinic = clinicRepository.findById(model.getClinicId())
                    .orElseThrow(() -> new IllegalArgumentException("clinic not found"));
            Appointment appointment = persist(model, false);
            appointment.setAppointmentType(type);
            appointment.setClinic(clinic);
            appointment.setSeriesId(seriesId);
            appointments.add(appointment);
        });
        return appointmentRepository.saveAll(appointments);
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
