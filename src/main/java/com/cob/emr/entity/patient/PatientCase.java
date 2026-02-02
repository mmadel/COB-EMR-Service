package com.cob.emr.entity.patient;

import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.enums.InjuryCase;
import com.cob.emr.enums.PlaceOfService;
import com.cob.emr.model.patient.cases.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name = "patient_case")
@Getter
@Setter
public class PatientCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "place_of_service")
    private PlaceOfService placeOfService;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id", referencedColumnName = "id")
    private ClinicalEMRUser clinicalEMRUser;
    @ManyToOne
    @JoinColumn(name="patient_id")
    private Patient patient;

    @Column(name = "authorization_counter")
    private Long authorizationCounter;

    @Column(name = "additional_info", length = 700)
    private String additionalInfo	;

    @Column(name = "`order`")
    private Integer order;

    @PrePersist
    public void generateUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }
}
