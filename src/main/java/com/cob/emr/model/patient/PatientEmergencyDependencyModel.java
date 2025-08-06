package com.cob.emr.model.patient;

import com.cob.emr.enums.PhoneType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class PatientEmergencyDependencyModel implements PatientDependencyModel {
    private String contactName;
    private PhoneType phoneType;

    private String phoneNumber;

    private String otherPhoneType;

    private String additionalInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientEmergencyDependencyModel that = (PatientEmergencyDependencyModel) o;
        return Objects.equals(contactName, that.contactName) && phoneType == that.phoneType && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(otherPhoneType, that.otherPhoneType) && Objects.equals(additionalInfo, that.additionalInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactName, phoneType, phoneNumber, otherPhoneType, additionalInfo);
    }
}
