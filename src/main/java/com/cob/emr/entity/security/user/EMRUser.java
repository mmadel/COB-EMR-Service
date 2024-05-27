package com.cob.emr.entity.security.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class EMRUser {
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "email")
    private String email;
    @Column(name = "uuid")
    private String uuid;
}
