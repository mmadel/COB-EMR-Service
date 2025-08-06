package com.cob.emr.controller.medical.note;

import com.cob.emr.model.medical.note.soap.field.SOAPField;
import com.cob.emr.model.medical.note.soap.field.SOAPSection;
import com.cob.emr.usecases.medical.note.*;
import com.cob.emr.usecases.medical.note.creator.DailyNoteCreator;
import com.cob.emr.usecases.medical.note.soap.profile.FindObjectiveProfileByNameUseCase;
import com.cob.emr.usecases.medical.note.soap.profile.FindObjectiveProfileUseCase;
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
    @Autowired
    UpdateSOAPCategoryFieldUseCase updateSOAPCategoryFieldUseCase;

    @Autowired
    GenerateSOAPFieldUseCase GenerateSOAPFieldUseCase;

    @Autowired
    CreateMedicalNoteUseCase createMedicalNoteUseCase;
    @Autowired
    FindObjectiveProfileUseCase findObjectiveProfileUseCase;
    @Autowired
    FindObjectiveProfileByNameUseCase findObjectiveProfileByNameUseCase;

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

    @PostMapping("/add/soap/field/category/{category}")
    public ResponseEntity addField(@RequestBody SOAPField model, @PathVariable String category) {
        updateSOAPCategoryFieldUseCase.update(model, category);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/find/section/{section}")
    public ResponseEntity getFieldBySection(@PathVariable String section) {
        return ResponseEntity.ok()
                .body(findSOAPFieldsBySectionUseCase.find(section));
    }


    @GetMapping("/find/objective/profile")
    public ResponseEntity getObjectiveProfile() {
        return ResponseEntity.ok()
                .body(findObjectiveProfileUseCase.findAll());
    }

    @GetMapping("/find/objective/profile/name/{name}")
    public ResponseEntity getObjectiveProfileByName(@PathVariable String name) {
        return ResponseEntity.ok()
                .body(findObjectiveProfileByNameUseCase.find(name));
    }
}
