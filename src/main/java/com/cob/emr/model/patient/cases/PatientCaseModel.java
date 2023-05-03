package com.cob.emr.model.patient.cases;

import com.cob.emr.enums.InjuryCase;
import com.cob.emr.enums.PlaceOfService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PatientCaseModel {

    private Long id;


    private String title;

    private PlaceOfService placeOfService;

    private TreatingDoctorModel treatingDoctor;

    private InjuryCase injuryCase;


    private CaseInsuranceInformationModel caseInsuranceInformation;

    private List<CaseDiagnosis> caseDiagnosis;

    private ReferralCase referralCase;

    private CaseOtherInformation caseOtherInformation;
}
