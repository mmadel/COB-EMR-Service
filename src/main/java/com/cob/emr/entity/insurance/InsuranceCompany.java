package com.cob.emr.entity.insurance;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.model.common.AddressDependencyModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity(name = "insurance_company")
@Getter
@Setter
public class InsuranceCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "insurance_company_type")
    private String insuranceType;
    @Column(name = "phone")
    private String phone;
    @Column(name = "fax")
    private String fax;
    @Column(name = "patient_addresses", columnDefinition = "json")
    @Type(type = "json")
    private List<AddressDependencyModel> addresses;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
