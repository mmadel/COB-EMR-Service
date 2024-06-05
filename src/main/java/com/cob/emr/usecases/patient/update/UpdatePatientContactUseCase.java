package com.cob.emr.usecases.patient.update;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.common.AddressModel;
import com.cob.emr.model.common.ContactModel;
import com.cob.emr.model.patient.PatientModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UpdatePatientContactUseCase implements PatientDependencyUpdater {

    @Override
    public void update(PatientModel model, Patient patient) {
        List<ContactModel> updated = model.getContacts();
        List<ContactModel> original = patient.getContacts();
        List<ContactModel> addedItems = findAddedContacts(updated, original);
        if (!addedItems.isEmpty())
            addedItems.stream().forEach(contactModel -> patient.getContacts().add(contactModel));
        List<ContactModel> deletedItems = findDeletedContacts(updated, original);
        if (!deletedItems.isEmpty())
            deletedItems.stream().forEach(contactModel -> patient.getContacts().remove(contactModel));
    }

    private List<ContactModel> findAddedContacts(List<ContactModel> updated, List<ContactModel> original) {
        return updated.stream()
                .filter(item -> !original.contains(item))
                .collect(Collectors.toList());
    }

    private List<ContactModel> findDeletedContacts(List<ContactModel> updated, List<ContactModel> original) {
        return original.stream()
                .filter(item -> !updated.contains(item))
                .collect(Collectors.toList());
    }
}
