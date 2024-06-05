package com.cob.emr.usecases.patient.update;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.exception.business.PatientCaseException;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.usecases.patient.update.cases.UpdatePatientCaseUseCase;
import com.cob.emr.usecases.patient.update.clinic.UpdatePatientClinicUseCase;
import com.cob.emr.usecases.patient.update.dependencies.UpdatePatientDependenciesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdatePatientUseCase {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    UpdatePatientDependenciesUseCase updatePatientDependenciesUseCase;
    @Autowired
    UpdatePatientCaseUseCase updatePatientCaseUseCase;
    @Autowired
    UpdatePatientClinicUseCase updatePatientClinicUseCase;

    public void update(PatientModel model) throws PatientCaseException {
        Patient patient = patientRepository.findById(model.getId()).get();
        updatePatientDependenciesUseCase.update(model, patient);
        updatePatientCaseUseCase.update(model, patient);
        updatePatientClinicUseCase.update(model, patient);
        fillPatientBasicInformation(model, patient);
        fillPatientIdentificationInformation(model, patient);
        patientRepository.save(patient);
    }

    private void fillPatientBasicInformation(PatientModel source, Patient target) {
        target.setFirstName(source.getFirstName());
        target.setMiddleName(source.getMiddleName());
        target.setLastName(source.getLastName());
        target.setBirthDate(source.getBirthDate());
        target.setGender(source.getGender());
        target.setMaritalStatus(source.getMaritalStatus());
        target.setSuffix(source.getSuffix());
        target.setEmployerName(source.getEmployerName());
        target.setTitle(source.getTitle());

    }

    private void fillPatientIdentificationInformation(PatientModel source, Patient target) {
        target.setPatientId(source.getPatientId());
        target.setEffectiveFromDate(source.getEffectiveFromDate());
        target.setEffectiveToDate(source.getEffectiveToDate());
    }
}
