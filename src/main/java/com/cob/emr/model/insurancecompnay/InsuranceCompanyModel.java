package com.cob.emr.model.insurancecompnay;

import com.cob.emr.enums.InsuranceCompanyType;
import com.cob.emr.model.common.AddressDependencyModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class InsuranceCompanyModel {

    private Long id;

    private String name;

    private InsuranceCompanyType insuranceType;

    private String phone;

    private String fax;

    private List<AddressDependencyModel> addresses;

    private Long organizationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InsuranceCompanyModel that = (InsuranceCompanyModel) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
