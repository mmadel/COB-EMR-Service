package com.cob.emr.entity.organization;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
}
