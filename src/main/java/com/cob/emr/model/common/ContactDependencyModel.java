package com.cob.emr.model.common;

import com.cob.emr.enums.PhoneType;
import com.cob.emr.model.patient.PatientDependencyModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class ContactDependencyModel implements PatientDependencyModel {

    private PhoneType phoneType;
    private String phoneNumber;
    private String email;

    private String additionalInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDependencyModel that = (ContactDependencyModel) o;
        return phoneType == that.phoneType && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(email, that.email) && Objects.equals(additionalInfo, that.additionalInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneType, phoneNumber, email, additionalInfo);
    }
}
