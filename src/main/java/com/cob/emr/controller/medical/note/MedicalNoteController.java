package com.cob.emr.controller.medical.note;

import com.cob.emr.model.medical.note.SOAPField;
import com.cob.emr.model.medical.note.SOAPSection;
import com.cob.emr.usecases.medical.note.CreateSOAPFieldsUseCase;
import com.cob.emr.usecases.medical.note.CreateSoapSectionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medical/note")
public class MedicalNoteController {
    @Autowired
    CreateSoapSectionUseCase createSoapSectionUseCase;
    @Autowired
    CreateSOAPFieldsUseCase createSOAPFieldsUseCase;

    @PostMapping("/create/soap/section")
    public ResponseEntity createSection(@RequestBody SOAPSection model) {
        createSoapSectionUseCase.create(model);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/create/soap/fields/category/{category}")
    public ResponseEntity createFields(@RequestBody List<SOAPField> models, @PathVariable String category) {
        createSOAPFieldsUseCase.create(models, category);
        return ResponseEntity.ok()
                .build();
    }
}
