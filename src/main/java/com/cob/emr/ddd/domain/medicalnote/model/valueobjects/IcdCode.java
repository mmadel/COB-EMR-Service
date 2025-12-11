package com.cob.emr.ddd.domain.medicalnote.model.valueobjects;

import lombok.Getter;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IcdCode implements Comparable<IcdCode> {
    private final String code;           // "M54.50"
    private final String description;    // "Low back pain, unspecified"
    private final Integer order;         // 0, 1, 2...

    @Override
    public int compareTo(IcdCode other) {
        return Integer.compare(this.order, other.order);
    }

    @Override
    public String toString() {
        return code + " - " + description;
    }
}