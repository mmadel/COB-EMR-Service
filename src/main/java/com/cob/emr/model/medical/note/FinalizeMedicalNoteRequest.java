package com.cob.emr.model.medical.note;

import com.cob.emr.enums.MedicalNoteType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FinalizeMedicalNoteRequest {
    private Long id;
    private Long caseId;
    private MedicalNoteType noteType;
    private String finalizedBy;
}
