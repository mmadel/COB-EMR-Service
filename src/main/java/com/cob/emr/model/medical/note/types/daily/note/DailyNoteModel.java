package com.cob.emr.model.medical.note.types.daily.note;

import com.cob.emr.model.medical.note.IMedicalNote;
import com.cob.emr.model.medical.note.types.initial.evaluation.assessment.Assessment;
import com.cob.emr.model.medical.note.types.initial.evaluation.plan.PlanOfCare;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Subjective;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DailyNoteModel implements IMedicalNote {
    private Object subjective;
    private Object objective;
    private Object assessment;
    private Object planOfCare;
}
