package com.cob.emr.model.patient;

import com.cob.emr.enums.*;
import com.cob.emr.model.common.AddressModel;
import com.cob.emr.model.common.ContactModel;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.model.patient.insurance.PatientInsuranceModel;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@Setter
@Getter
public class PatientModel {

    private Long id;
    private String fullName;
    private String firstName;
    private String middleName;
    private String lastName;
    private Long birthDate;
    private MaritalStatus maritalStatus;
    private Suffix suffix;
    private String employerName;
    private Title title;
    private IdType idType;
    private Gender gender;
    private String patientId;
    private Long effectiveFromDate;
    private Long effectiveToDate;
    private List<AddressModel> addresses;
    private List<ContactModel> contacts;
    private List<PatientEmergencyModel> emergencies;
    private PatientDependentModel dependent;
    private List<Long> clinicsId = new ArrayList<>();
    private List<PatientCaseModel> cases = new ArrayList<>();
    private List<PatientInsuranceModel> patientInsuranceModels = new ArrayList<>();
}
