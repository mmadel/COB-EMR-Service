package com.cob.emr.model.insurancecompnay;

import com.cob.emr.enums.InsuranceCompanyType;
import com.cob.emr.model.common.AddressModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class InsuranceCompanyModel {

    private Long id;

    private String name;

    private InsuranceCompanyType insuranceType;

    private String phone;

    private String fax;

    private List<AddressModel> addresses;

    private Long clinicId;
}
