package com.cob.emr.repositories.medical.note.test;

import com.cob.emr.entity.medical.note.test.OMTTestValuesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OMTTestValuesEntityRepository extends CrudRepository<OMTTestValuesEntity, Long> {

    Optional<OMTTestValuesEntity> findByMedicalNote_IdAndTestName(Long medicalNoteId, String testName);

}
