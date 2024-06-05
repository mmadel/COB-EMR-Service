package com.cob.emr.usecases.patient.update.dependencies;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.patient.insurance.PatientInsuranceDependencyModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdatePatientInsuranceUseCase extends PatientDependencyUpdater<PatientInsuranceDependencyModel> {
    @Override
    protected void update(PatientModel model, Patient patient) {
        List<PatientInsuranceDependencyModel> updated = model.getPatientInsuranceModels();
        List<PatientInsuranceDependencyModel> original = patient.getPatientInsuranceModels();

        List<PatientInsuranceDependencyModel> addedItems = findAdded(updated, original);
        if (!addedItems.isEmpty())
            addedItems.stream().forEach(patientInsuranceModel -> patient.getPatientInsuranceModels().add(patientInsuranceModel));

        List<PatientInsuranceDependencyModel> deletedItems = findDeleted(updated, original);
        if (!deletedItems.isEmpty())
            deletedItems.stream().forEach(patientInsuranceModel -> patient.getPatientInsuranceModels().remove(patientInsuranceModel));
    }
}
