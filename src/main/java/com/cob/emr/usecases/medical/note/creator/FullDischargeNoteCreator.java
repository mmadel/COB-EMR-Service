package com.cob.emr.usecases.medical.note.creator;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.FullDischargeNote;
import com.cob.emr.entity.medical.note.types.ProgressNote;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.model.medical.note.types.initial.evaluation.billing.Billing;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.usecases.medical.note.finder.FindRecentProgressNoteUseCase;
import com.cob.emr.usecases.medical.note.updater.objective.ResetAssessmentUseCase;
import com.cob.emr.usecases.medical.note.updater.objective.ResetEvaluationReEvaluationCodeUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FullDischargeNoteCreator implements MedicalNoteCreator{
    @Autowired
    FindRecentProgressNoteUseCase findRecentProgressNoteUseCase;
    @Autowired
    private ResetEvaluationReEvaluationCodeUseCase resetEvaluationReEvaluationCodeUseCase;
    @Autowired
    private ResetAssessmentUseCase resetAssessmentUseCase;
    @Override
    public MedicalNote createNote(MedicalNoteRequest medicalNoteRequest) throws MedicalNoteException {
        MedicalNote medicalNote = findRecentProgressNoteUseCase.find(medicalNoteRequest.getCaseId());
        if(medicalNote == null)
            throw new MedicalNoteException(HttpStatus.CONFLICT, MedicalNoteException.DISCHARGE_NOTE_ERROR, new Object[]{});
        ProgressNote progressNote =(ProgressNote)medicalNote;
        FullDischargeNote fullDischargeNote = new FullDischargeNote();
        fullDischargeNote.setPatientCase(medicalNote.getPatientCase());
        fullDischargeNote.setType(MedicalNoteType.DISCHARGE_NOTE);
        fullDischargeNote.setCreatedBy(medicalNoteRequest.getCreatedBy());
        fullDischargeNote.setNoteDate(medicalNoteRequest.getNoteDate());
        fullDischargeNote.setSubjective(progressNote.getSubjective());
        fullDischargeNote.setObjective(progressNote.getObjective());
        fullDischargeNote.setBilling(progressNote.getBilling());

        fullDischargeNote.setAssessment(progressNote.getAssessment());
        fullDischargeNote.setPlanOfCare(progressNote.getPlanOfCare());

        //reset UntimedCodes val and re-val code
        Billing billing = (Billing) resetEvaluationReEvaluationCodeUseCase.resetUntimedCodes(fullDischargeNote.getBilling());
        if (billing != null)
            fullDischargeNote.setBilling(billing);
        //reset Assessment Diagnosis values
        Object assessment = resetAssessmentUseCase.resetDiagnosis(fullDischargeNote.getAssessment());
        if (assessment != null)
            fullDischargeNote.setAssessment(assessment);
        return fullDischargeNote;
    }
}
