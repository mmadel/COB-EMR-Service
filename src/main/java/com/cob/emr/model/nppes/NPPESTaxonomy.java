package com.cob.emr.model.nppes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NPPESTaxonomy {
    private String code;
    @JsonProperty("taxonomy_group")
    private String taxonomyGroup;
    private String desc;
    private String state;
    private String license;
    private Boolean primary;
}
