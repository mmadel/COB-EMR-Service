package com.cob.emr.entity.clinic;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.model.common.AddressModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
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

    @ManyToMany
    @JoinTable(name = "clinic_patient",
            joinColumns = {@JoinColumn(name = "fk_clinic")},
            inverseJoinColumns = {@JoinColumn(name = "fk_patient")})
    private Set<Patient> patients = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

}
