package com.cob.emr.model.common;

import com.cob.emr.enums.AddressType;
import com.cob.emr.model.patient.DependencyPatientModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
public class AddressModel implements Serializable, DependencyPatientModel {

    private AddressType addressType;
    private String firstAddress;

    private String secondAddress;

    private String country;

    private String city;
    private String province;
    private String zipCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressModel that = (AddressModel) o;
        return addressType == that.addressType && Objects.equals(firstAddress, that.firstAddress) && Objects.equals(secondAddress, that.secondAddress) && Objects.equals(country, that.country) && Objects.equals(city, that.city) && Objects.equals(province, that.province) && Objects.equals(zipCode, that.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressType, firstAddress, secondAddress, country, city, province, zipCode);
    }
}
