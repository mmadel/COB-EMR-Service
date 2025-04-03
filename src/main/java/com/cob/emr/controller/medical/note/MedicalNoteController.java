package com.cob.emr.controller.medical.note;

import com.cob.emr.model.medical.note.soap.field.FieldRequest;
import com.cob.emr.model.medical.note.soap.field.Item;
import com.cob.emr.model.medical.note.soap.field.SOAPField;
import com.cob.emr.model.medical.note.soap.field.SOAPSection;
import com.cob.emr.model.medical.note.soap.field.request.BillingFieldRequest;
import com.cob.emr.model.medical.note.soap.field.request.DependencyRequest;
import com.cob.emr.usecases.medical.note.*;
import com.cob.emr.usecases.medical.note.soap.FindSOAPFieldByIdUseCase;
import com.cob.emr.usecases.medical.note.soap.FindSOAPFieldUseCase;
import com.cob.emr.usecases.medical.note.soap.profile.FindObjectiveProfileByNameUseCase;
import com.cob.emr.usecases.medical.note.soap.profile.FindObjectiveProfileUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    CreateCategoryFieldUseCase createCategoryFieldUseCase;
    @Autowired
    GenerateSOAPFieldUseCase GenerateSOAPFieldUseCase;
    @Autowired
    CreateBillingSOAPFieldUseCase createBillingSOAPFieldUseCase;
    @Autowired
    CreateMedicalNoteUseCase createMedicalNoteUseCase;
    @Autowired
    FindObjectiveProfileUseCase findObjectiveProfileUseCase;
    @Autowired
    FindObjectiveProfileByNameUseCase findObjectiveProfileByNameUseCase;
    @Autowired
    UpdateSOAPFieldDependenciesUseCase updateSOAPFieldDependenciesUseCase;
    @Autowired
    FindSOAPFieldUseCase findSOAPFieldUseCase;
    @Autowired
    FindSOAPFieldByIdUseCase findSOAPFieldByIdUseCase;

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

    @PostMapping("/create-soap-field/category/{category}")
    public ResponseEntity addFullField(@RequestBody List<FieldRequest> requests, @PathVariable String category) {
        return ResponseEntity.ok()
                .body(createCategoryFieldUseCase.create(requests, category));
    }

    @PostMapping("/update-soap-field/field-id/{fieldId}/category/{category}")
    public ResponseEntity updateField(@RequestBody List<FieldRequest> requests, @PathVariable String fieldId, @PathVariable String category) {
        return ResponseEntity.ok()
                .body(updateSOAPFieldDependenciesUseCase.update(requests, fieldId, category));
    }
    @GetMapping("/find-soap-field/field-name/{name}")
    public ResponseEntity findField( @PathVariable String name) {
        return ResponseEntity.ok()
                .body(findSOAPFieldUseCase.find(name));
    }
    @GetMapping("/find-soap-field/field-id/{id}")
    public ResponseEntity findFieldById( @PathVariable String id) {
        return ResponseEntity.ok()
                .body(findSOAPFieldByIdUseCase.find(id));
    }

    @PostMapping("/create-billing-soap-field/category/{category}")
    public ResponseEntity addBillingField(@RequestBody List<BillingFieldRequest> requests, @PathVariable String category) {
        return ResponseEntity.ok()
                .body(createBillingSOAPFieldUseCase.create(requests, category));
    }

    @GetMapping("/find/section/{section}")
    public ResponseEntity getFieldBySection(@PathVariable String section) {
        return ResponseEntity.ok()
                .body(findSOAPFieldsBySectionUseCase.find(section));
    }

    @PostMapping("/construct-soap-field")
    public ObjectNode generateDependencies(@RequestBody DependencyRequest request) {
        if (request.dependencies == null || request.dependencies.isEmpty()) {
            throw new IllegalArgumentException("Invalid input: dependencies list cannot be empty");
        }

        Item root = GenerateSOAPFieldUseCase.buildTree(request.dependencies);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.valueToTree(root);
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
