package com.cob.emr.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
}
