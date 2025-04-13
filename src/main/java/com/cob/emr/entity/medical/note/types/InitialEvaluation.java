package com.cob.emr.entity.medical.note.types;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.model.medical.note.types.initial.evaluation.assessment.Assessment;
import com.cob.emr.model.medical.note.types.initial.evaluation.billing.Billing;
import com.cob.emr.model.medical.note.types.initial.evaluation.plan.PlanOfCare;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Subjective;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "initial_evaluation")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Setter
@Getter
public class InitialEvaluation extends MedicalNote {
    @Column(name = "subjective", columnDefinition = "json")
    @Type(type = "json")
    private Subjective subjective;

    @Column(name = "assessment", columnDefinition = "json")
    @Type(type = "json")
    private Assessment assessment;

    @Column(name = "plan_of_care", columnDefinition = "json")
    @Type(type = "json")
    private PlanOfCare planOfCare;

    @Column(name = "billing", columnDefinition = "json")
    @Type(type = "json")
    private Billing billing;

    @Column(name = "objective", columnDefinition = "json")
    @Type(type = "json")
    private Object objective;
}
