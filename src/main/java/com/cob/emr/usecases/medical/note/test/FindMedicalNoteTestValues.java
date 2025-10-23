package com.cob.emr.usecases.medical.note.test;

import com.cob.emr.entity.medical.note.test.OMTTestValuesEntity;
import com.cob.emr.model.medical.note.omt.test.OMTTestValues;
import com.cob.emr.repositories.medical.note.test.OMTTestValuesEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindMedicalNoteTestValues {
    @Autowired
    OMTTestValuesEntityRepository omtTestValuesEntityRepository;
    public OMTTestValues find(Long medicalNoteId , String testName){
        OMTTestValuesEntity omtTestValuesEntity = omtTestValuesEntityRepository.findByMedicalNote_IdAndTestName(medicalNoteId,testName)
                .orElseThrow(() -> new IllegalArgumentException("Not Found"));
        OMTTestValues omtTestValues = new OMTTestValues();
        omtTestValues.setId(omtTestValuesEntity.getId());
        omtTestValues.setMedicalNoteId(omtTestValuesEntity.getMedicalNote().getId());
        omtTestValues.setValues(omtTestValuesEntity.getTestValues());
        omtTestValues.setTestName(omtTestValuesEntity.getTestName());
        return omtTestValues;
    }
}
