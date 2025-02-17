package com.cob.emr.model.patient;

import com.cob.emr.enums.*;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.common.AddressDependencyModel;
import com.cob.emr.model.common.ContactDependencyModel;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.model.patient.insurance.PatientInsuranceDependencyModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Setter
@Getter
public class PatientModel {

    private Long id;
    private String fullName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String displayName;
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
    private List<AddressDependencyModel> addresses;
    private List<ContactDependencyModel> contacts;
    private List<PatientEmergencyDependencyModel> emergencies;
    private PatientDependentModel dependent;
    private List<Long> clinicsId = new ArrayList<>();
    private List<ClinicModel> clinicModels = new ArrayList<>();
    private List<PatientCaseModel> cases = new ArrayList<>();
    private List<PatientInsuranceDependencyModel> patientInsuranceModels = new ArrayList<>();
}
