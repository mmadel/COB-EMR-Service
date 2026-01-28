package com.cob.emr.model.patient.document;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PatientCaseDocumentResponse {

    private List<PatientCaseDocumentModel> allList;
    private List<PatientCaseDocumentModel> caseList;
}
