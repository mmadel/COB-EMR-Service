package com.cob.emr.model.clinic;

import com.cob.emr.model.common.AddressModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class ClinicModel {
    private Long id;
    private String name;
    private AddressModel address;
    private Long organizationId;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClinicModel that = (ClinicModel) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
