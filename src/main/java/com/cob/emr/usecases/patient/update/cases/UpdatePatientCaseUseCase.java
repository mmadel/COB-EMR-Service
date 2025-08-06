package com.cob.emr.usecases.patient.update.cases;

import com.cob.emr.entity.appointment.Appointment;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.exception.business.PatientCaseException;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.repositories.appointment.AppointmentRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UpdatePatientCaseUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientCaseRepository patientCaseRepository;
    @Autowired
    AppointmentRepository appointmentRepository;

    @Transactional
    public void update(PatientModel model, Patient patient) throws PatientCaseException {

        List<Long> updated = new ArrayList<>(model.getCases())
                .stream()
                .map(patientCase -> patientCase.getId())
                .collect(Collectors.toList());

        List<Long> original = new ArrayList<>(patient.getCases())
                .stream()
                .map(patientCase -> patientCase.getId())
                .collect(Collectors.toList());

        List<PatientCaseModel> addedItems = findAdded(model.getCases());
        if (!addedItems.isEmpty()) {
            addedItems.forEach(patientCaseModel -> {
                PatientCase patientCase = mapper.map(patientCaseModel, PatientCase.class);
                patient.addCase(patientCase);
            });
        }

        List<Long> deletedItems = findDeleted(updated, original);
        if (!deletedItems.isEmpty()) {
            Optional<List<Appointment>> appointments = appointmentRepository.findAppointmentByCases(deletedItems);
            if (appointments.isEmpty())
                throw new PatientCaseException(HttpStatus.CONFLICT, PatientCaseException.CASE_ASSIGN_TO_APPOINTMENT, null);
            deletedItems.forEach(patientCaseId -> {
                PatientCase patientCase = patientCaseRepository.findById(patientCaseId).get();
                patientCase.getPatient().getCases().remove(patientCase);
                patientCaseRepository.delete(patientCase);
            });
        }
    }

    private List<PatientCaseModel> findAdded(List<PatientCaseModel> cases) {
        return cases.stream()
                .filter(item -> item.getId() == null)
                .collect(Collectors.toList());
    }

    protected List<Long> findDeleted(List<Long> updated, List<Long> original) {
        return original.stream()
                .filter(item -> !updated.contains(item))
                .collect(Collectors.toList());
    }
}
