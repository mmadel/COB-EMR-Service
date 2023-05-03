package com.cob.emr.entity.patient;

import com.cob.emr.enums.InjuryCase;
import com.cob.emr.enums.PlaceOfService;
import com.cob.emr.model.patient.cases.TreatingDoctorModel;
import com.cob.emr.model.patient.cases.CaseDiagnosis;
import com.cob.emr.model.patient.cases.CaseInsuranceInformationModel;
import com.cob.emr.model.patient.cases.CaseOtherInformation;
import com.cob.emr.model.patient.cases.ReferralCase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity(name = "patient_case")
@Getter
@Setter
public class PatientCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "place_of_service")
    private PlaceOfService placeOfService;

    @Column(name = "treating_doctor", columnDefinition = "json")
    @Type(type = "json")
    private TreatingDoctorModel treatingDoctor;

    @Enumerated(EnumType.STRING)
    @Column(name = "injury_case")
    private InjuryCase injuryCase;


    @Column(name = "case_insurance_information", columnDefinition = "json")
    @Type(type = "json")
    private CaseInsuranceInformationModel caseInsuranceInformation;

    @Column(name = "case_diagnosis", columnDefinition = "json")
    @Type(type = "json")
    private List<CaseDiagnosis> caseDiagnosis;

    @Column(name = "referral_case", columnDefinition = "json")
    @Type(type = "json")
    private ReferralCase referralCase;

    @Column(name = "case_other_information", columnDefinition = "json")
    @Type(type = "json")
    private CaseOtherInformation caseOtherInformation;


}
