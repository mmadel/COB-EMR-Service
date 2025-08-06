package com.cob.emr.model.clinic;

import com.cob.emr.model.user.ClinicalEMRUserModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClinicDataHolder {
    ClinicModel clinicModel;
    ClinicalEMRUserModel administratorDoctor;
}
