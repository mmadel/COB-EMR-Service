package com.cob.emr.usecases.medical.note.creator;

import com.cob.emr.enums.MedicalNoteType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicalNoteFactoryProvider {
    @Autowired
    private InitialEvaluationCreator initialEvaluationFactory;
    @Autowired
    private DailyNoteCreator dailyNoteCreatorFactory;
    @Autowired
    private ProgressNoteCreator progressNoteCreatorFactory;
    @Autowired
    QuickDischargeCreator quickDischargeCreatorFactory;

    public MedicalNoteCreator getFactory(MedicalNoteType noteType) {
        switch (noteType) {
            case INITIAL_EVALUATION:
                return initialEvaluationFactory;
            case DAILY_NOTE:
                return dailyNoteCreatorFactory;
            case PROGRESS_NOTE:
                return progressNoteCreatorFactory;
            case QUICK_DISCHARGE_NOTE:
                return quickDischargeCreatorFactory;
            default:
                throw new IllegalArgumentException("Unsupported Note Type");
        }
    }
}
