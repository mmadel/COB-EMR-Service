package com.cob.emr.model.medical.note;

import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.model.medical.note.types.initial.evaluation.assessment.Assessment;
import com.cob.emr.model.medical.note.types.initial.evaluation.billing.Billing;
import com.cob.emr.model.medical.note.types.initial.evaluation.plan.PlanOfCare;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Subjective;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicalNoteRequest {
    private Long id;
    private Long caseId;
    private MedicalNoteType noteType;
    private String createdBy;
    private Long noteDate;

    private Subjective subjective;
    private Assessment assessment;
    private PlanOfCare planOfCare;
    private Billing billing;
    private Object objective;
}
