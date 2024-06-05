package com.cob.emr.usecases.patient.update;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.common.AddressModel;
import com.cob.emr.model.patient.PatientModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdatePatientAddressUseCase extends PatientDependencyUpdater<AddressModel> {
    @Override
    public void update(PatientModel model, Patient patient) {
        List<AddressModel> updated = model.getAddresses();
        List<AddressModel> original = patient.getAddresses();
        List<AddressModel> addedItems = findAdded(updated, original);
        if (!addedItems.isEmpty())
            addedItems.stream().forEach(addressModel -> patient.getAddresses().add(addressModel));
        List<AddressModel> deletedItems = findDeleted(updated, original);
        if (!deletedItems.isEmpty())
            deletedItems.stream().forEach(addressModel -> patient.getAddresses().remove(addressModel));
    }

}
