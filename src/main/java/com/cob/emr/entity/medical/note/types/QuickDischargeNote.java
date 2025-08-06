package com.cob.emr.entity.medical.note.types;

import com.cob.emr.entity.medical.note.MedicalNote;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "quick_discharge_note")
@Setter
@Getter
public class QuickDischargeNote extends MedicalNote {
    @Column(name = "discharge_date")
    private Long dischargeDate;

    @Column(name = "number_of_visits")
    private Integer numberOfVisits;

    @Column(name = "comment")
    private String comment;

}
