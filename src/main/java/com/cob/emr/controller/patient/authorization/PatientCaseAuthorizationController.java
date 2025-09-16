package com.cob.emr.controller.patient.authorization;


import com.cob.emr.model.patient.authorization.PatientCaseAuthorizationDTO;
import com.cob.emr.service.patient.authorization.PatientCaseAuthorizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auths")
public class PatientCaseAuthorizationController {

    private final PatientCaseAuthorizationService service;

    public PatientCaseAuthorizationController(PatientCaseAuthorizationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PatientCaseAuthorizationDTO> create(@Valid @RequestBody PatientCaseAuthorizationDTO dto) {
        PatientCaseAuthorizationDTO created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientCaseAuthorizationDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PatientCaseAuthorizationDTO dto) {
        PatientCaseAuthorizationDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<PatientCaseAuthorizationDTO>> list() {
        List<PatientCaseAuthorizationDTO> list = service.listAll();
        return ResponseEntity.ok(list);
    }
}
