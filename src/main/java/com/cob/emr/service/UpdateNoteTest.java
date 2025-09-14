package com.cob.emr.service;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Subjective;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateNoteTest {
    @Autowired
    MedicalNoteRepository medicalNoteRepository;

    public void dd(Long id, Subjective subjective) {
        InitialEvaluation note = (InitialEvaluation) medicalNoteRepository.findById(id).get();
        note.setSubjective(subjective);
        medicalNoteRepository.save(note);
    }
}
