package com.cob.emr.model.nppes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NPPESResponse {
    @JsonProperty("result_count")
    private Integer resultCount;
    private List<NPPESResult> results;
}
