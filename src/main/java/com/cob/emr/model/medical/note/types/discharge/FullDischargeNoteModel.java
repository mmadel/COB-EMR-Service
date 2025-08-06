package com.cob.emr.model.medical.note.types.discharge;

import com.cob.emr.model.medical.note.IMedicalNote;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FullDischargeNoteModel extends IMedicalNote {
    private Object subjective;
    private Object objective;
    private Object assessment;
    private Object planOfCare;
    private Object billing;
}
