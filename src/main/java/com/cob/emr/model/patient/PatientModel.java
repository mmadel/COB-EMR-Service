package com.cob.emr.model.patient;

import com.cob.emr.enums.IdType;
import com.cob.emr.enums.MaritalStatus;
import com.cob.emr.enums.Suffix;
import com.cob.emr.enums.Title;
import com.cob.emr.model.common.AddressModel;
import com.cob.emr.model.common.ContactModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class PatientModel {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private Date birthDate;

    private MaritalStatus maritalStatus;


    private Suffix suffix;


    private String employerName;


    private Title title;

    private IdType idType;

    private String PatientId;


    private Long effectiveFromDate;

    private Long effectiveToDate;


    private List<AddressModel> addresses;


    private List<ContactModel> contacts;

    private List<PatientEmergencyModel> emergencies;

    private PatientDependentModel dependent;


    private Set<Long> clinicsId = new HashSet<>();
}
