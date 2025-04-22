package com.cob.emr.controller.soap;

import com.cob.emr.exception.business.SOAPException;
import com.cob.emr.model.medical.note.soap.field.FieldRequest;
import com.cob.emr.model.medical.note.soap.field.Item;
import com.cob.emr.model.medical.note.soap.field.request.BillingFieldRequest;
import com.cob.emr.model.medical.note.soap.field.request.DependencyRequest;
import com.cob.emr.usecases.medical.note.*;
import com.cob.emr.usecases.medical.note.soap.*;
import com.cob.emr.usecases.medical.note.soap.profile.FindObjectiveProfileByNameUseCase;
import com.cob.emr.usecases.medical.note.soap.profile.UnAssignSOAPFieldFromProfileUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/soap")
public class SOAPController {
    @Autowired
    FindSOAPFieldUseCase findSOAPFieldUseCase;
    @Autowired
    FindSOAPFieldByIdUseCase findSOAPFieldByIdUseCase;
    @Autowired
    CreateCategoryFieldUseCase createCategoryFieldUseCase;
    @Autowired
    UpdateSOAPFieldDependenciesUseCase updateSOAPFieldDependenciesUseCase;
    @Autowired
    UpdateSOAPFieldNameUseCase updateSOAPFieldNameUseCase;
    @Autowired
    AssignSOAPFieldsByCategoryToProfileUseCase assignSOAPFieldsByCategoryToProfileUseCase;
    @Autowired
    GenerateSOAPFieldsStyleUseCase generateSOAPFieldsStyleUseCase;
    @Autowired
    FindObjectiveProfileByNameUseCase findObjectiveProfileByNameUseCase;
    @Autowired
    GenerateSOAPFieldUseCase GenerateSOAPFieldUseCase;
    @Autowired
    CreateBillingSOAPFieldUseCase createBillingSOAPFieldUseCase;
    @Autowired
    AssignSOAPFieldToProfileUseCase assignSOAPFieldToProfileUseCase;
    @Autowired
    UnAssignSOAPFieldFromProfileUseCase unAssignSOAPFieldFromProfileUseCase;
    @Autowired
    FindSOAPFieldIDUseCase findSOAPFieldIDUseCase;
    @Autowired
    CreateFieldDependenciesUseCase createFieldDependenciesUseCase;

    @GetMapping("/find/field/name/{name}")
    public ResponseEntity findField(@PathVariable String name) {
        return ResponseEntity.ok()
                .body(findSOAPFieldUseCase.find(name));
    }

    @GetMapping("/find/field/id/{id}")
    public ResponseEntity findFieldById(@PathVariable String id) {
        return ResponseEntity.ok()
                .body(findSOAPFieldByIdUseCase.find(id));
    }

    @PostMapping("/create/field/category/{category}")
    public ResponseEntity addFullField(@RequestBody List<FieldRequest> requests, @PathVariable String category) {
        return ResponseEntity.ok()
                .body(createCategoryFieldUseCase.create(requests, category));
    }

    @GetMapping("/find/field/displayName/{displayName}/category/{category}")
    public ResponseEntity findFieldId(@PathVariable String displayName, @PathVariable String category) throws SOAPException {
        return ResponseEntity.ok()
                .body(findSOAPFieldIDUseCase.find(displayName, category));
    }

    @GetMapping({"/create/field/dependencies/parent/{parentId}/category/{categoryName}",
            "/create/field/dependencies/parent/{parentId}/category/{categoryName}/profile/{profile}"})
    public ResponseEntity CreateFieldDependencies(@RequestBody List<FieldRequest> requests,
                                                  @PathVariable String parentId,
                                                  @PathVariable String categoryName,
                                                  @PathVariable(name = "profile", required = false) String profile) throws SOAPException {
        return ResponseEntity.ok()
                .body(createFieldDependenciesUseCase.create(requests, parentId, categoryName,profile));
    }

    @PostMapping("/update/field/id/{fieldId}/category/name/{category}")
    public ResponseEntity updateField(@RequestBody List<FieldRequest> requests, @PathVariable String fieldId, @PathVariable String category) {
        return ResponseEntity.ok()
                .body(updateSOAPFieldDependenciesUseCase.update(requests, fieldId, category));
    }

    @PostMapping("/update/field-name/category/{categoryId}")
    public ResponseEntity update(@PathVariable Long categoryId) {
        updateSOAPFieldNameUseCase.update(categoryId);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/assign/fields/profile/name/{profile}/category/id/{categoryId}")
    public ResponseEntity assignFieldsByCategory(@PathVariable Long categoryId, @PathVariable String profile) {
        assignSOAPFieldsByCategoryToProfileUseCase.assign(profile, categoryId);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/assign/field/profile/name/{profile}/field/id/{fieldId}")
    public ResponseEntity assignSingle(@PathVariable String fieldId, @PathVariable String profile) {
        assignSOAPFieldToProfileUseCase.assign(profile, fieldId);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/unassign/field/profile/id/{profile}/field/id/{fieldId}")
    public ResponseEntity unAssign(@PathVariable String fieldId, @PathVariable Long profile) {
        unAssignSOAPFieldFromProfileUseCase.unAssign(fieldId, profile);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/find/field-name/profile/name/{profile}")
    public ResponseEntity findSOAPFieldsByProfile(@PathVariable String profile) {
        return ResponseEntity.ok()
                .body(findObjectiveProfileByNameUseCase.findFields(profile));
    }

    @GetMapping("/generate/field-name/style/category/id/{categoryId}")
    public ResponseEntity generateSOAPFieldStyle(@PathVariable Long categoryId) {
        return ResponseEntity.ok()
                .body(generateSOAPFieldsStyleUseCase.generate(categoryId));
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

    @PostMapping("/create-billing-soap-field/category/{category}")
    public ResponseEntity addBillingField(@RequestBody List<BillingFieldRequest> requests, @PathVariable String category) {
        return ResponseEntity.ok()
                .body(createBillingSOAPFieldUseCase.create(requests, category));
    }
}
