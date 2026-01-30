package com.cob.emr.model.patient;

import com.cob.emr.enums.SearchType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientSearchModel {
    private String firstName;
    private String lastName;
    private Long dateOfBirth;
    private Long insuranceCompanyId;
    private Long therapistId;
    private String phoneNumber;
    private SearchType searchType;
}
