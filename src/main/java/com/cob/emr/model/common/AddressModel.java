package com.cob.emr.model.common;

import com.cob.emr.enums.AddressType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class AddressModel implements Serializable {

    private AddressType addressType;
    private String firstAddress;

    private String secondAddress;

    private String country;

    private String city;
    private String province;

}
