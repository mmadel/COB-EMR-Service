package com.cob.emr.usecases.medical.note.creator;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.entity.medical.note.types.ProgressNote;
import com.cob.emr.enums.MedicalNoteType;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.model.medical.note.types.initial.evaluation.billing.Billing;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Subjective;
import com.cob.emr.usecases.medical.note.finder.FindFinalizeInitialExamNoteUseCase;
import com.cob.emr.usecases.medical.note.finder.FindRecentProgressNoteUseCase;
import com.cob.emr.usecases.medical.note.updater.objective.CloneSubjectiveComplaintsUseCase;
import com.cob.emr.usecases.medical.note.updater.objective.ResetAssessmentUseCase;
import com.cob.emr.usecases.medical.note.updater.objective.ResetEvaluationReEvaluationCodeUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProgressNoteCreator implements MedicalNoteCreator {

    @Autowired
    private FindRecentProgressNoteUseCase findRecentProgressNoteUseCase;
    @Autowired
    private FindFinalizeInitialExamNoteUseCase findFinalizeInitialExamNoteUseCase;
    @Autowired
    private CloneSubjectiveComplaintsUseCase cloneSubjectiveComplaintsUseCase;
    @Autowired
    private ResetAssessmentUseCase resetAssessmentUseCase;
    @Autowired
    private ResetEvaluationReEvaluationCodeUseCase resetEvaluationReEvaluationCodeUseCase;

    @Override
    public MedicalNote createNote(MedicalNoteRequest medicalNoteRequest) throws MedicalNoteException {
        MedicalNote medicalNote = getProperMedicalNote(medicalNoteRequest.getCaseId());
        return create(medicalNote);
    }

    private MedicalNote getProperMedicalNote(Long caseId) throws MedicalNoteException {
        MedicalNote medicalNote = findRecentProgressNoteUseCase.find(caseId);
        if (medicalNote != null)
            return medicalNote;
        else
            return findFinalizeInitialExamNoteUseCase.find(caseId);
    }

    private ProgressNote create(MedicalNote medicalNote) {
        ProgressNote progressNote = new ProgressNote();
        progressNote.setPatientCase(medicalNote.getPatientCase());
        progressNote.setType(MedicalNoteType.PROGRESS_NOTE);
        progressNote.setCreatedBy(medicalNote.getCreatedBy());
        progressNote.setDateOfService(medicalNote.getDateOfService());
        if (medicalNote instanceof ProgressNote)
            createFromProgress(progressNote, (ProgressNote) medicalNote);
        if (medicalNote instanceof InitialEvaluation)
            createFromInitialEvaluation(progressNote, (InitialEvaluation) medicalNote);

        //reset UntimedCodes val and re-val code
        Billing billing = (Billing) resetEvaluationReEvaluationCodeUseCase.resetUntimedCodes(progressNote.getBilling());
        if (billing != null)
            progressNote.setBilling(billing);
        //reset Assessment Diagnosis values
        Object assessment = resetAssessmentUseCase.resetDiagnosis(progressNote.getAssessment());
        if (assessment != null)
            progressNote.setAssessment(assessment);
        return progressNote;
    }

    private void createFromProgress(ProgressNote target, ProgressNote source) {
        target.setSubjective(source.getSubjective());
        target.setObjective(source.getObjective());
        target.setAssessment(source.getAssessment());
        target.setPlanOfCare(source.getPlanOfCare());
        target.setBilling(source.getBilling());
    }

    private void createFromInitialEvaluation(ProgressNote target, InitialEvaluation source) {
        target.setSubjective(source.getSubjective());
        target.setObjective(source.getObjective());
        target.setAssessment(source.getAssessment());
        target.setPlanOfCare(source.getPlanOfCare());
        target.setBilling(source.getBilling());
        Subjective subjective = cloneSubjectiveComplaintsUseCase.cloneComplaints(source.getSubjective());
        if (subjective != null)
            target.setSubjective(subjective);
    }
}
