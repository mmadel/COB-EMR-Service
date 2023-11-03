package com.cob.emr.entity.security.role;

import com.cob.emr.entity.security.resource.RoleResource;
import com.cob.emr.entity.security.user.ClinicalUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "role")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    private String name;
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<ClinicalUser> clinicalUsers = new HashSet<>();

    @OneToMany(mappedBy = "role",fetch = FetchType.EAGER)
    private Set<RoleResource> roleResources = new HashSet<>();
}
