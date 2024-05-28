package com.cob.emr.entity.security.user;

import com.cob.emr.entity.clinic.Clinic;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clinical_emr_user")
@Setter
@Getter
public class ClinicalEMRUser extends EMRUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name="npi")
    private String npi;
    @Column(name="licence")
    private String licence;
    @Column(name="speciality")
    private String speciality;
    @Column(name="credential")
    private String credential;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE}, mappedBy = "clinicalUsers")
    private Set<Clinic> clinics = new HashSet<>();

    public void addClinic(Clinic clinic) {
        this.clinics.add(clinic);
        clinic.getClinicalUsers().add(this);
    }

    public void removeClinic(Clinic clinic) {
        this.clinics.remove(clinic);
        clinic.getClinicalUsers().remove(this);
    }
}
