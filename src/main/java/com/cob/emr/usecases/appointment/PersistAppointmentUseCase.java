package com.cob.emr.usecases.appointment;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.entity.calendar.CalendarEntity;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.enums.AppointmentStatus;
import com.cob.emr.enums.AppointmentStructure;
import com.cob.emr.model.appointment.AppointmentModel;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import com.cob.emr.repositories.calendar.CalendarRepository;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.PatientRepository;
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

    public List<AppointmentModel> persist(List<AppointmentModel> models, AppointmentModel model) {
        List<Appointment> appointments = new ArrayList<>();
        if (models.size() > 1)
            createRepeatedAppointment(models, appointments, model.getSeriesId());
        else
            createSingleAppointment(models
                    .stream()
                    .findFirst().get(), appointments);
        Iterable<Appointment> createdAppointments = appointmentRepository.saveAll(appointments);
        assignToCalendar(createdAppointments, model.getCalendarId());
        List<Appointment> list = StreamSupport.stream(createdAppointments.spliterator(), false)
                .collect(Collectors.toList());
        return list.stream()
                .map(appointment -> {
                    AppointmentModel appointmentModel = mapper.map(appointment, AppointmentModel.class);
                    appointmentModel.setAppointmentTypeColor(appointment.getAppointmentType().getColor());
                    appointmentModel.setAppointmentFontTypeColor(appointment.getAppointmentType().getFontColor());
                    appointmentModel.setAppointmentStructure(appointment.getPatient() == null ? AppointmentStructure.Block : AppointmentStructure.Full);
                    appointmentModel.setAppointmentType(appointment.getAppointmentType().getName());
                    return appointmentModel;
                }).collect(Collectors.toList());
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

    private void createSingleAppointment(AppointmentModel model, List<Appointment> appointments) {
        AppointmentType type = appointmentTypeRepository.findById(model.getAppointmentTypeId())
                .orElseThrow(() -> new IllegalArgumentException("appointment not found"));
        Clinic clinic = clinicRepository.findById(model.getClinicId())
                .orElseThrow(() -> new IllegalArgumentException("clinic not found"));
        Appointment appointment = persist(model, false);
        appointment.setAppointmentType(type);
        appointment.setClinic(clinic);
        appointments.add(appointment);
    }

    private void createRepeatedAppointment(List<AppointmentModel> models, List<Appointment> appointments, Integer seriesId) {
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
