package com.cob.emr.usecases.patient.update;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.common.AddressModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UpdatePatientAddressUseCase {
    public void update(List<AddressModel> updated, List<AddressModel> original, Patient patient) {
        List<AddressModel> addedItems = findAddedAddress(updated, original);
        if (!addedItems.isEmpty())
            addedItems.stream().forEach(addressModel -> patient.getAddresses().add(addressModel));
        List<AddressModel> deletedItems = findDeletedAddress(updated, original);
        if (!deletedItems.isEmpty())
            deletedItems.stream().forEach(addressModel -> patient.getAddresses().remove(addressModel));
    }

    private List<AddressModel> findAddedAddress(List<AddressModel> updated, List<AddressModel> original) {
        return updated.stream()
                .filter(item -> !original.contains(item))
                .collect(Collectors.toList());
    }

    private List<AddressModel> findDeletedAddress(List<AddressModel> updated, List<AddressModel> original) {
        return original.stream()
                .filter(item -> !updated.contains(item))
                .collect(Collectors.toList());
    }
}
