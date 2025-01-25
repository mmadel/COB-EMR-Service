package com.cob.emr.usecases.appointment.series;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.appointment.AppointmentType;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.appointment.AppointmentTypeModel;
import com.cob.emr.model.appointment.series.AppointmentFullSeries;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.appointment.AppointmentTypeRepository;
import com.cob.emr.usecases.patient.cases.FindPatientCasesUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindFullAppointmentSeriesUseCase {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    ModelMapper mapper;

    public AppointmentFullSeries find(Long clinicId, Integer seriesId) {
        Appointment appointment = appointmentRepository.findAppointmentSeries(clinicId, seriesId)
                .stream().findFirst().get();
        AppointmentFullSeries series = new AppointmentFullSeries();
        fillPatientCase(series, appointment.getPatient());
        fillDates(series, appointment.getStartDate(), appointment.getEndDate());
        fillAppointmentType(series, appointment.getAppointmentType());
        series.setTherapy(appointment.getTherapyUUID());
        series.setClinic(mapper.map(appointment.getClinic(), ClinicModel.class));
        return series;
    }

    private void fillPatientCase(AppointmentFullSeries appointmentFullSeries, Patient patient) {
        List<PatientCaseModel> patientCases = patient.getCases().stream()
                .map(patientCase -> mapper.map(patientCase, PatientCaseModel.class))
                .collect(Collectors.toList());
        appointmentFullSeries.setPatientCases(patientCases);
        appointmentFullSeries.setPatientName(patient.getLastName() + "," + patient.getFirstName());
        List<ClinicModel> patientClinics = patient.getClinics().stream()
                .map(clinic -> mapper.map(clinic, ClinicModel.class))
                .collect(Collectors.toList());
        appointmentFullSeries.setPatientClinics(patientClinics);
    }

    private void fillDates(AppointmentFullSeries appointmentFullSeries, Long startDate, Long endDate) {
        appointmentFullSeries.setStartTime(startDate);
        appointmentFullSeries.setEndTime(endDate);
    }

    private void fillAppointmentType(AppointmentFullSeries appointmentFullSeries, AppointmentType appointmentType) {
        appointmentFullSeries.setAppointmentType(mapper.map(appointmentType, AppointmentTypeModel.class));
    }
}
