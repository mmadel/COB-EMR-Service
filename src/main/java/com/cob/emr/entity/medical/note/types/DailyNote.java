package com.cob.emr.entity.medical.note.types;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.model.medical.note.types.initial.evaluation.assessment.Assessment;
import com.cob.emr.model.medical.note.types.initial.evaluation.plan.PlanOfCare;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Subjective;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "daily_note")
@Setter
@Getter
public class DailyNote extends MedicalNote {
    @Column(name = "subjective", columnDefinition = "json")
    @Type(type = "json")
    private Object subjective;

    @Column(name = "objective", columnDefinition = "json")
    @Type(type = "json")
    private Object objective;

    @Column(name = "assessment", columnDefinition = "json")
    @Type(type = "json")
    private Object assessment;

    @Column(name = "plan_of_care", columnDefinition = "json")
    @Type(type = "json")
    private Object planOfCare;

}
