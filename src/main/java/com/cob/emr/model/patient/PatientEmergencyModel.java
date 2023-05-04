package com.cob.emr.model.patient;

import com.cob.emr.enums.PhoneType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientEmergencyModel {
    private String contactName;
    private PhoneType phoneType;

    private String phoneNumber;

    private String otherPhoneType;

    private String additionalInfo;
}
