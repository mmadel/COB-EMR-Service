package com.cob.emr.entity.security.resource;

import com.cob.emr.entity.security.role.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "role_resource")
@Setter
@Getter
public class RoleResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @Column(name = "can_add", columnDefinition = "tinyint(1) default 0", nullable = false)
    private Boolean add;
    @Column(name = "can_view", columnDefinition = "tinyint(1) default 0", nullable = false)
    private Boolean view;
    @Column(name = "can_modify", columnDefinition = "tinyint(1) default 0", nullable = false)
    private Boolean modify;
    @Column(name = "can_delete", columnDefinition = "tinyint(1) default 0", nullable = false)
    private Boolean delete;
}
