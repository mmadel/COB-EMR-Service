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
    public ResponseEntity<List<PatientCaseAuthorizationDTO>> create(@Valid @RequestBody List<PatientCaseAuthorizationDTO> dtos) {
        return ResponseEntity.ok()
                .body(service.saveOrUpdateList(dtos));
    }

    @GetMapping
    public ResponseEntity<List<PatientCaseAuthorizationDTO>> list() {
        List<PatientCaseAuthorizationDTO> list = service.getAll();
        return ResponseEntity.ok(list);
    }
}
