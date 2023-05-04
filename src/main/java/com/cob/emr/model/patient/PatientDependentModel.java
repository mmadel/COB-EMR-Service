package com.cob.emr.model.patient;

import com.cob.emr.model.common.AddressModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PatientDependentModel implements Serializable {

    private Boolean isDependent;

    private AddressModel address;

    private String phone;
}
