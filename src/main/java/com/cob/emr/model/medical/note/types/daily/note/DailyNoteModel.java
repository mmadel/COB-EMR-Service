package com.cob.emr.model.medical.note.types.daily.note;

import com.cob.emr.model.medical.note.IMedicalNote;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DailyNoteModel extends IMedicalNote {
    private Object subjective;
    private Object objective;
    private Object assessment;
    private Object planOfCare;
}
