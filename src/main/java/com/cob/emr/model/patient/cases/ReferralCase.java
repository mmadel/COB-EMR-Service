package com.cob.emr.model.patient.cases;

import com.cob.emr.enums.ReferringPartyType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReferralCase {
    private String referringPartyName;
    private String referringPartyNPI;
    private String phone;
    private String fax;
    private String email;

    private ReferringPartyType referringPartyType;

    private String doctorType;
}
