package com.cob.emr.model.response;

import com.cob.emr.model.user.ClericalEMRUserModel;
import com.cob.emr.model.user.ClinicalEMRUserModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ClericalUserResponse {
    int number_of_records;
    long number_of_matching_records;
    List<ClericalEMRUserModel> records;
}
