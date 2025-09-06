package com.cob.emr.entity.medical.note.finalize.init.exam.plan;

import com.cob.emr.model.medical.note.finalize.init.exam.plan.InitPlanOfCare;
import com.cob.emr.model.medical.note.finalize.init.exam.score.InitOMTScore;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "initial_examination_plan_of_care")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class InitialExaminationPlanOfCareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "initial_examination_note_id")
    private Long initialExaminationNoteId;

    @Column(name = "plan_of_care_data", columnDefinition = "json")
    @Type(type = "json")
    private InitPlanOfCare initPlanOfCareData;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @PrePersist
    private void beforeSaving() {
        createdAt = new Date().getTime();
    }
    @PreUpdate
    private void beforeUpdating() {
        updatedAt = new Date().getTime();
    }
}
