package com.cob.emr.entity.medical.note.summary;


import com.cob.emr.entity.medical.note.summary.patient.summary.PatientSummaryModel;
import com.cob.emr.model.medical.note.summary.soap.SubjectiveSummary;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "initial_examination_summary")
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class)
})
@Getter
@Setter
public class InitialExaminationSummaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "subjective_summary", columnDefinition = "json")
    @Type(type = "json")
    private SubjectiveSummary subjective;

    @Column(name = "patient_date", columnDefinition = "json")
    @Type(type = "json")
    private PatientSummaryModel patientData;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "note_id")
    private Long noteId;

    @PrePersist
    private void beforeSaving() {
        createdAt = new Date().getTime();
    }

    @PreUpdate
    private void beforeUpdating() {
        updatedAt = new Date().getTime();
    }
}
