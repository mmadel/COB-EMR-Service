package com.cob.emr.model.nppes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NPPESAddress {
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("country_name")
    private String countryName;
    @JsonProperty("address_purpose")
    private String addressPurpose;
    @JsonProperty("address_type")
    private String addressType;
    @JsonProperty("address_1")
    private String address1;
    @JsonProperty("address_2")
    private String address2;
    private String city;
    private String state;
    @JsonProperty("postal_code")
    private String postalCode;
    @JsonProperty("telephone_number")
    private String telephoneNumber;
    @JsonProperty("fax_number")
    private String faxNumber;
}
