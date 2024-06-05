package com.cob.emr.usecases.patient.update;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.patient.PatientEmergencyDependencyModel;
import com.cob.emr.model.patient.PatientModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdatePatientEmergencyUseCase extends PatientDependencyUpdater<PatientEmergencyDependencyModel> {

    @Override
    public void update(PatientModel model, Patient patient) {
        List<PatientEmergencyDependencyModel> updated = model.getEmergencies();
        List<PatientEmergencyDependencyModel> original = patient.getEmergencies();

        List<PatientEmergencyDependencyModel> addedItems = findAdded(updated, original);
        if (!addedItems.isEmpty())
            addedItems.stream().forEach(patientEmergencyModel -> patient.getEmergencies().add(patientEmergencyModel));

        List<PatientEmergencyDependencyModel> deletedItems = findDeleted(updated, original);
        if (!deletedItems.isEmpty())
            deletedItems.stream().forEach(patientEmergencyModel -> patient.getEmergencies().remove(patientEmergencyModel));
    }
}
