package com.cob.emr.model.user;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ClericalEMRUserModel {
    private Long id;

    private String accountName;

    private String fullName;

    private String email;

    private String uuid;

    private List<Long> clinicsId = new ArrayList<>();
}
