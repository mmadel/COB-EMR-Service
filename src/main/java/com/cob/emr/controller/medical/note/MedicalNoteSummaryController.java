package com.cob.emr.controller.medical.note;

import com.cob.emr.usecases.medical.note.summary.FindInitialExaminationPlanOfCareUseCase;
import com.cob.emr.usecases.medical.note.summary.FindInitialExaminationScoreUseCase;
import com.cob.emr.usecases.medical.note.summary.FindInitialExaminationSummaryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medical/note/summary")
public class MedicalNoteSummaryController {
    @Autowired
    FindInitialExaminationScoreUseCase findInitialExaminationScoreUseCase;
    @Autowired
    FindInitialExaminationPlanOfCareUseCase findInitialExaminationPlanOfCareUseCase;
    @Autowired
    FindInitialExaminationSummaryUseCase findInitialExaminationSummaryUseCase;

    @GetMapping("/find/score/{scoreId}")
    public ResponseEntity getScore(@PathVariable Long scoreId) {
        return ResponseEntity.ok()
                .body(findInitialExaminationScoreUseCase.find(scoreId));
    }

    @GetMapping("/find/plan/{planId}")
    public ResponseEntity getPlanOfCare(@PathVariable Long planId) {
        return ResponseEntity.ok()
                .body(findInitialExaminationPlanOfCareUseCase.find(planId));
    }

    @GetMapping("/find/initial/examination/{noteId}")
    public ResponseEntity getInitialExaminationSummary(@PathVariable Long noteId) {
        return ResponseEntity.ok()
                .body(findInitialExaminationSummaryUseCase.find(noteId));
    }
}
