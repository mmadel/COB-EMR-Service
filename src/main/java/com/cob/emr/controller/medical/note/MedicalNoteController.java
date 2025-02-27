package com.cob.emr.controller.medical.note;

import com.cob.emr.model.medical.note.SOAPSection;
import com.cob.emr.usecases.medical.note.CreateSoapSectionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medical/note")
public class MedicalNoteController {
    @Autowired
    CreateSoapSectionUseCase createSoapSectionUseCase;

    @PostMapping("/create/soap")
    public ResponseEntity create(@RequestBody SOAPSection model) {
        createSoapSectionUseCase.create(model);
        return ResponseEntity.ok()
                .build();
    }
}
