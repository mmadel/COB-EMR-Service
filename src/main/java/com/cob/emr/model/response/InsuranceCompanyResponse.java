package com.cob.emr.model.response;

import com.cob.emr.model.insurancecompnay.InsuranceCompanyModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class InsuranceCompanyResponse {
    Integer number_of_records;
    Integer number_of_matching_records;
    List<InsuranceCompanyModel> records;

}
