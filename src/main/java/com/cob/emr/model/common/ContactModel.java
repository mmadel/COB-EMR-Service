package com.cob.emr.model.common;

import com.cob.emr.enums.PhoneType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContactModel {

    private PhoneType phoneType;
    private String phoneNumber;
    private String email;

    private String additionalInfo;
}
