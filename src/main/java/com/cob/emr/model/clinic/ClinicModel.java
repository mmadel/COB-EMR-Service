package com.cob.emr.model.clinic;

import com.cob.emr.model.common.AddressModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClinicModel {
    private Long id;
    private String name;
    private String address;

    private Long organizationId;


}
