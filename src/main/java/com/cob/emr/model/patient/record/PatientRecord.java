package com.cob.emr.model.patient.record;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class PatientRecord {
    private Long entityId;
    private String noteId;
    private String status;
    private Long date;
    private LocalDate dos;
    private boolean isNote;
    private List<String> actions;
}
