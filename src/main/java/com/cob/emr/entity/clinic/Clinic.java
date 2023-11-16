package com.cob.emr.entity.clinic;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.security.user.ClinicalUser;
import com.cob.emr.model.common.AddressModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "clinic_name")
    private String name;

    @Column(name = "clinic_address", columnDefinition = "json")
    @Type(type = "json")
    private AddressModel address;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "clinic_patient",
            joinColumns = {@JoinColumn(name = "clinic_id")},
            inverseJoinColumns = {@JoinColumn(name = "patient_id")})
    private Set<Patient> patients = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "clinic_user",
            joinColumns = {@JoinColumn(name = "clinic_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<ClinicalUser> users = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public void addPatient(Patient patient) {
        this.patients.add(patient);
        patient.getClinics().add(this);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Clinic clinic = (Clinic) o;
//        return id.equals(clinic.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name);
//    }
}
