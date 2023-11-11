package com.cob.emr.model.clinic;

import com.cob.emr.entity.security.user.DoctorUser;
import com.cob.emr.model.common.AddressModel;
import com.cob.emr.model.user.AdministratorDoctor;
import com.cob.emr.model.user.ClinicalUserModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ClinicModel {
    private Long id;
    private String name;
    private AddressModel address;

    private Long organizationId;

    private AdministratorDoctor administratorDoctor;


}
