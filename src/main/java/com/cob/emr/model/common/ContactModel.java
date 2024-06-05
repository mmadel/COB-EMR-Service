package com.cob.emr.model.common;

import com.cob.emr.enums.PhoneType;
import com.cob.emr.model.patient.DependencyPatientModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class ContactModel implements DependencyPatientModel {

    private PhoneType phoneType;
    private String phoneNumber;
    private String email;

    private String additionalInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactModel that = (ContactModel) o;
        return phoneType == that.phoneType && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(email, that.email) && Objects.equals(additionalInfo, that.additionalInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneType, phoneNumber, email, additionalInfo);
    }
}
