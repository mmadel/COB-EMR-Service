package com.cob.emr.usecases.medical.note.test;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.entity.medical.note.test.OMTTestValuesEntity;
import com.cob.emr.model.medical.note.omt.test.OMTTestValues;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.medical.note.test.OMTTestValuesEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveOMTTestValues {
    @Autowired
    OMTTestValuesEntityRepository omtTestValuesEntityRepository;
    @Autowired
    MedicalNoteRepository medicalNoteRepository;

    public void save(OMTTestValues omtTestValues) {
        MedicalNote medicalNote = medicalNoteRepository.findById(omtTestValues.getMedicalNoteId())
                .orElseThrow(() -> new IllegalArgumentException(""));
        OMTTestValuesEntity omtTestValuesEntity = new OMTTestValuesEntity();
        omtTestValuesEntity.setId(omtTestValues.getId());
        omtTestValuesEntity.setMedicalNote(medicalNote);
        omtTestValuesEntity.setTestName(omtTestValues.getTestName());
        omtTestValuesEntity.setTestValues(omtTestValues.getValues());
        omtTestValuesEntityRepository.save(omtTestValuesEntity);
    }
}
