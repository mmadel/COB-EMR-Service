package com.cob.emr.entity.referring.provider;

import com.cob.emr.entity.organization.Organization;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class ReferringProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "npi")
    private String npi;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
