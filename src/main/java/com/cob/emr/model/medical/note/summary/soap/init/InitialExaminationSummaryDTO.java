package com.cob.emr.model.medical.note.summary.soap.init;

import com.cob.emr.entity.medical.note.summary.patient.summary.PatientSummaryModel;
import com.cob.emr.model.medical.note.finalize.init.exam.plan.InitAssessment;
import com.cob.emr.model.medical.note.summary.soap.ObjectiveSummary;
import com.cob.emr.model.medical.note.summary.soap.PlanOfCareSummary;
import com.cob.emr.model.medical.note.summary.soap.SubjectiveSummary;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InitialExaminationSummaryDTO {
    private SubjectiveSummary subjective;
    private ObjectiveSummary objective;
    private PlanOfCareSummary planOfCare;
    private InitAssessment assessmentSummary;
    private PatientSummaryModel patientData;
}
