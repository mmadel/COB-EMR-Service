package com.cob.emr.model.user;

import com.cob.emr.model.clinic.ClinicModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ClinicalUserModel {
    private Long id;
    private String userName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String uuid;
    private String password;
    private String role;
    private List<ClinicModel> clinics;
}
