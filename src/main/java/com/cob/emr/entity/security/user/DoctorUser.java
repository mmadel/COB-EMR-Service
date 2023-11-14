package com.cob.emr.entity.security.user;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "doctor_user")
@Setter
@Getter
public class DoctorUser extends ClinicalUser {
    @Column(name="npi")
    private String npi;
    @Column(name="licence")
    private String licence;
    @Column(name="speciality")
    private String speciality;
    @Column(name="credential")
    private String credential;
}
