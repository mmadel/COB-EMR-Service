package com.cob.emr.entity.patient;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.enums.IdType;
import com.cob.emr.enums.MaritalStatus;
import com.cob.emr.enums.Suffix;
import com.cob.emr.enums.Title;
import com.cob.emr.model.common.AddressModel;
import com.cob.emr.model.common.ContactModel;
import com.cob.emr.model.patient.PatientDependentModel;
import com.cob.emr.model.patient.PatientEmergencyModel;
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

    @Column(name = "patient_id_effective_from")
    private Long effectiveFromDate;
    @Column(name = "patient_id_effective_to")
    private Long effectiveToDate;

    @Column(name = "patient_addresses", columnDefinition = "json")
    @Type(type = "json")
    private List<AddressModel> addresses;

    @Column(name = "patient_contacts", columnDefinition = "json")
    @Type(type = "json")
    private List<ContactModel> contacts;
    @Column(name = "patient_emergency", columnDefinition = "json")
    @Type(type = "json")
    private List<PatientEmergencyModel> emergencies;
    @Column(name = "patient_dependent", columnDefinition = "json")
    @Type(type = "json")
    private PatientDependentModel dependent;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "patients")
    private Set<Clinic> clinics = new HashSet<>();


    public void addClinic(Clinic clinic) {
        this.clinics.add(clinic);
        clinic.getPatients().add(this);
    }

    public void removeClinic(Clinic clinic) {
        this.clinics.remove(clinic);
        clinic.getPatients().remove(this);
    }

}
