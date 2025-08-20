package com.cob.emr.model.medical.note.soap.field;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SoapFieldsByMedicalNoteRequest {
    private List<OrderField> fields;
    private Long categoryId;
    private Long medicalNotType;
}
