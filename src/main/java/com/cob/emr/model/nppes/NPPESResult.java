package com.cob.emr.model.nppes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NPPESResult {
    private String number;
    @JsonProperty("enumeration_type")
    private String enumerationType;
    private NPPESBasic basic;
    private List<NPPESAddress> addresses;
    private List<NPPESTaxonomy> taxonomies;
}
