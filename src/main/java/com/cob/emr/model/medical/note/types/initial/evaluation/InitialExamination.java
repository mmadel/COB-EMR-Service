package com.cob.emr.model.medical.note.types.initial.evaluation;

import com.cob.emr.model.medical.note.IMedicalNote;
import com.cob.emr.model.medical.note.types.initial.evaluation.assessment.Assessment;
import com.cob.emr.model.medical.note.types.initial.evaluation.billing.Billing;
import com.cob.emr.model.medical.note.types.initial.evaluation.plan.PlanOfCare;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Subjective;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitialExamination implements IMedicalNote {
    private Subjective subjective;

    private Assessment assessment;
    private PlanOfCare planOfCare;
    private Billing billing;
}
