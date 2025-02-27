package com.cob.emr.controller.medical.note;

import com.cob.emr.model.medical.note.SOAPField;
import com.cob.emr.model.medical.note.SOAPSection;
import com.cob.emr.usecases.medical.note.CreateSOAPFieldsUseCase;
import com.cob.emr.usecases.medical.note.CreateSoapSectionUseCase;
import com.cob.emr.usecases.medical.note.FindSOAPFieldsBySectionUseCase;
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
    @Autowired
    FindSOAPFieldsBySectionUseCase findSOAPFieldsBySectionUseCase;

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

    @GetMapping("/find/section/{section}")
    public ResponseEntity getFieldBySection(@PathVariable String section) {
        return ResponseEntity.ok()
                .body(findSOAPFieldsBySectionUseCase.find(section));
    }
}
