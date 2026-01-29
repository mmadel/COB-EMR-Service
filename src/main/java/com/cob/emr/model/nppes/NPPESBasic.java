package com.cob.emr.model.nppes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NPPESBasic {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("middle_name")
    private String middleName;
    private String credential;
    private String gender;
    @JsonProperty("enumeration_date")
    private String enumerationDate;
    @JsonProperty("last_updated")
    private String lastUpdated;
    private String status;
    @JsonProperty("name_prefix")
    private String namePrefix;
    @JsonProperty("sole_proprietor")
    private String soleProprietor;
    @JsonProperty("organization_name")
    private String organizationName;
}
