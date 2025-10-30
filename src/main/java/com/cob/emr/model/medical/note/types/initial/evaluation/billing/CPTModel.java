package com.cob.emr.model.medical.note.types.initial.evaluation.billing;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CPTModel {
    private String cpt;
    private String description;
    private Integer quantity;
}
