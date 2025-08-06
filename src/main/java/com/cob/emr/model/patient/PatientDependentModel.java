package com.cob.emr.model.patient;

import com.cob.emr.model.common.AddressDependencyModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PatientDependentModel implements Serializable {

    private Boolean isDependent;

    private AddressDependencyModel address;

    private String phone;
}
