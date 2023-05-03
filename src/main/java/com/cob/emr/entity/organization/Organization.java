package com.cob.emr.entity.organization;

import com.cob.emr.entity.clinic.Clinic;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "organization_name")
    private String name;
    @Column(name = "organization_dba")
    private String dba;

    @Column(name = "organization_group_npi")
    private String groupNPI;

    @Column(name = "organization_tax_id")
    private String taxID;

    @OneToMany(mappedBy = "organization")
    private List<Clinic> clinics = new ArrayList<>();
}
