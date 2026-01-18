package com.cob.emr.entity.patient;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.enums.*;
import com.cob.emr.model.common.AddressDependencyModel;
import com.cob.emr.model.common.ContactDependencyModel;
import com.cob.emr.model.patient.PatientDependentModel;
import com.cob.emr.model.patient.PatientEmergencyDependencyModel;
import com.cob.emr.model.patient.insurance.PatientInsuranceDependencyModel;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity(name = "patient")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;

    @Column
    private Long birthDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "suffix")
    private Suffix suffix;

    @Column(name = "employer_name")
    private String employerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "title")
    private Title title;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_type")
    private IdType idType;
    @Column(name = "patient_id")
    private String PatientId;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "patient_id_effective_from")
    private Long effectiveFromDate;
    @Column(name = "patient_id_effective_to")
    private Long effectiveToDate;

    @Column(name = "patient_addresses", columnDefinition = "json")
    @Type(type = "json")
    private List<AddressDependencyModel> addresses;

    @Column(name = "patient_contacts", columnDefinition = "json")
    @Type(type = "json")
    private List<ContactDependencyModel> contacts;
    @Column(name = "patient_emergency", columnDefinition = "json")
    @Type(type = "json")
    private List<PatientEmergencyDependencyModel> emergencies;
    @Column(name = "patient_dependent", columnDefinition = "json")
    @Type(type = "json")
    private PatientDependentModel dependent;

    @Column(name = "patient_insurances", columnDefinition = "json")
    @Type(type = "json")
    private List<PatientInsuranceDependencyModel> patientInsuranceModels;
    @ManyToMany
    @JoinTable(
            name = "patient_clinic",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "clinic_id")
    )
    private Set<Clinic> clinics = new HashSet<>();

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<PatientCase> cases = new HashSet<>();

    public void addClinic(Clinic clinic) {
        this.clinics.add(clinic);
        clinic.getPatients().add(this);
    }

    public void removeClinic(Clinic clinic) {
        this.clinics.remove(clinic);
        clinic.getPatients().remove(this);
    }

    public void addCase(PatientCase patientCase) {
        this.cases.add(patientCase);
        patientCase.setPatient(this);
    }

    public void removeCase(PatientCase patientCase) {
        this.cases.remove(patientCase);
        patientCase.setPatient(null);
    }

    @PrePersist
    public void generateUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }

}
