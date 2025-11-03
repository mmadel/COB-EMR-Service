package com.cob.emr.entity.medical.note.types;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.model.medical.note.types.initial.evaluation.billing.Billing;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Subjective;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "progress_note")
@Setter
@Getter
public class ProgressNote extends MedicalNote {
    @Column(name = "subjective", columnDefinition = "json")
    @Type(type = "json")
    private Subjective subjective;

    @Column(name = "objective", columnDefinition = "json")
    @Type(type = "json")
    private Object objective;

    @Column(name = "assessment", columnDefinition = "json")
    @Type(type = "json")
    private Object assessment;

    @Column(name = "plan_of_care", columnDefinition = "json")
    @Type(type = "json")
    private Object planOfCare;

    @Column(name = "billing", columnDefinition = "json")
    @Type(type = "json")
    private Billing billing;
}
