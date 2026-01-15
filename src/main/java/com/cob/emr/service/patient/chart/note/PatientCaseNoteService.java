package com.cob.emr.service.patient.chart.note;

import com.cob.emr.model.patient.chart.note.PatientChartNoteRequest;

public interface PatientCaseNoteService {
    public void create(PatientChartNoteRequest patientChartNoteRequest);
    public void update(PatientChartNoteRequest patientChartNoteRequest);
}
