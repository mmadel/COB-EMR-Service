package com.cob.emr.usecases.medical.note.post.init.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.enums.MedicalNoteStatus;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.usecases.medical.note.creator.DailyNoteCreator;
import com.cob.emr.usecases.medical.note.post.init.note.plan.CreateInitialExaminationPlanOfCareUseCase;
import com.cob.emr.usecases.medical.note.post.init.note.score.CreateInitialExaminationScoreUseCase;
import com.cob.emr.usecases.medical.note.summary.initial.examination.CreateInitialExaminationSummaryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;

@Component
public class PostFinalizeInitialNoteUseCase {
    @Autowired
    private DailyNoteCreator dailyNoteCreator;
    @Autowired
    private MedicalNoteRepository medicalNoteRepository;
    @Autowired
    private CreateInitialExaminationPlanOfCareUseCase createInitialExaminationPlanOfCareUseCase;
    @Autowired
    private CreateInitialExaminationScoreUseCase createInitialExaminationScoreUseCase;
    @Autowired
    private CreateInitialExaminationSummaryUseCase createInitialExaminationSummaryUseCase;

    public void execute(Long caseId, Long noteId) throws MedicalNoteException, IOException {
        MedicalNoteRequest medicalNoteRequest = new MedicalNoteRequest();
        medicalNoteRequest.setCaseId(caseId);
        MedicalNote note = dailyNoteCreator.createNote(medicalNoteRequest);
        note.setStatus(MedicalNoteStatus.Finalized);
        medicalNoteRepository.save(note);
        InitialEvaluation initialEvaluation = (InitialEvaluation) medicalNoteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("Initial Evaluation Note Not Found"));
        Long scoreId = createInitialExaminationScoreUseCase.create(initialEvaluation);
        createInitialExaminationPlanOfCareUseCase.create(initialEvaluation);
        createInitialExaminationSummaryUseCase.create(initialEvaluation , scoreId);

    }
}
