package com.cob.emr.model.common;

import com.cob.emr.enums.AddressType;
import com.cob.emr.model.patient.PatientDependencyModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
public class AddressDependencyModel implements Serializable, PatientDependencyModel {

    private AddressType addressType;
    private String firstAddress;

    private String secondAddress;

    private String country;

    private String city;
    private String province;
    private String zipCode;
    private String state;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressDependencyModel that = (AddressDependencyModel) o;
        return addressType == that.addressType && Objects.equals(firstAddress, that.firstAddress) && Objects.equals(secondAddress, that.secondAddress) && Objects.equals(country, that.country) && Objects.equals(city, that.city) && Objects.equals(province, that.province) && Objects.equals(zipCode, that.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressType, firstAddress, secondAddress, country, city, province, zipCode);
    }
}
