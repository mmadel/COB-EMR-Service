package com.cob.emr.model.common;

import com.cob.emr.enums.AddressType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressModel {

    private AddressType addressType;
    private String firstAddress;

    private String secondAddress;

    private String country;

    private String city;
    private String province;

}
