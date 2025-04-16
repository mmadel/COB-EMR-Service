package com.cob.emr.model.medical.note.types.discharge;

import com.cob.emr.model.medical.note.IMedicalNote;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuickDischargeNoteModel implements IMedicalNote {

    private Long dischargeDate;

    private Integer numberOfVisits;

    private String comment;
}
