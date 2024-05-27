package com.cob.emr.entity.security.user;

import com.cob.emr.entity.clinic.Clinic;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clerical_emr_user")
public class ClericalEMRUser extends EMRUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "clericalUsers")
    protected Set<Clinic> clinics = new HashSet<>();

    public void addClinic(Clinic clinic) {
        this.clinics.add(clinic);
        clinic.getClericalUsers().add(this);
    }

    public void removeClinic(Clinic clinic) {
        this.clinics.remove(clinic);
        clinic.getClericalUsers().remove(this);
    }
}
