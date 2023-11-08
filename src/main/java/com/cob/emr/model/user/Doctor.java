package com.cob.emr.model.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Doctor {
    String npi;
    String licence;
    String speciality;
    String credential;
}
