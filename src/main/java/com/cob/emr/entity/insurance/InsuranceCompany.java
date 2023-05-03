package com.cob.emr.entity.insurance;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.enums.InsuranceCompanyType;
import com.cob.emr.model.common.AddressModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "insurance_company_type")
    private InsuranceCompanyType insuranceType;
    @Column(name = "phone")
    private String phone;
    @Column(name = "fax")
    private String fax;
    @Column(name = "patient_addresses", columnDefinition = "json")
    @Type(type = "json")
    private List<AddressModel> addresses;

    @ManyToMany(mappedBy = "insuranceCompanies")
    private Set<Clinic> clinics = new HashSet<>();
}
