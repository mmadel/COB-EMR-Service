package com.cob.emr.usecases.patient.update;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.patient.PatientEmergencyModel;
import com.cob.emr.model.patient.PatientModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdatePatientEmergencyUseCase extends PatientDependencyUpdater<PatientEmergencyModel> {

    @Override
    public void update(PatientModel model, Patient patient) {
        List<PatientEmergencyModel> updated = model.getEmergencies();
        List<PatientEmergencyModel> original = patient.getEmergencies();

        List<PatientEmergencyModel> addedItems = findAdded(updated, original);
        if (!addedItems.isEmpty())
            addedItems.stream().forEach(patientEmergencyModel -> patient.getEmergencies().add(patientEmergencyModel));

        List<PatientEmergencyModel> deletedItems = findDeleted(updated, original);
        if (!deletedItems.isEmpty())
            deletedItems.stream().forEach(patientEmergencyModel -> patient.getEmergencies().remove(patientEmergencyModel));
    }
}
