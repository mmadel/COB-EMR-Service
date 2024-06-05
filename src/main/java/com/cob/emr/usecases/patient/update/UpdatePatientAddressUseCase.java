package com.cob.emr.usecases.patient.update;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.common.AddressDependencyModel;
import com.cob.emr.model.patient.PatientModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdatePatientAddressUseCase extends PatientDependencyUpdater<AddressDependencyModel> {
    @Override
    public void update(PatientModel model, Patient patient) {
        List<AddressDependencyModel> updated = model.getAddresses();
        List<AddressDependencyModel> original = patient.getAddresses();
        List<AddressDependencyModel> addedItems = findAdded(updated, original);
        if (!addedItems.isEmpty())
            addedItems.stream().forEach(addressModel -> patient.getAddresses().add(addressModel));
        List<AddressDependencyModel> deletedItems = findDeleted(updated, original);
        if (!deletedItems.isEmpty())
            deletedItems.stream().forEach(addressModel -> patient.getAddresses().remove(addressModel));
    }

}
