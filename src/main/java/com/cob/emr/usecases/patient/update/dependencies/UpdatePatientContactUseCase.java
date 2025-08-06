package com.cob.emr.usecases.patient.update.dependencies;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.common.ContactDependencyModel;
import com.cob.emr.model.patient.PatientModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdatePatientContactUseCase extends PatientDependencyUpdater<ContactDependencyModel> {

    @Override
    public void update(PatientModel model, Patient patient) {
        List<ContactDependencyModel> updated = model.getContacts();
        List<ContactDependencyModel> original = patient.getContacts();
        List<ContactDependencyModel> addedItems = findAdded(updated, original);
        if (!addedItems.isEmpty())
            addedItems.stream().forEach(contactModel -> patient.getContacts().add(contactModel));
        List<ContactDependencyModel> deletedItems = findDeleted(updated, original);
        if (!deletedItems.isEmpty())
            deletedItems.stream().forEach(contactModel -> patient.getContacts().remove(contactModel));
    }
}
