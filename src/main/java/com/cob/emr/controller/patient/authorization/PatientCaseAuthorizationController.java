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

    @PostMapping("/case/{id}")
    public ResponseEntity<List<PatientCaseAuthorizationDTO>> create(@Valid @RequestBody List<PatientCaseAuthorizationDTO> dtos, @PathVariable Long id) {
        return ResponseEntity.ok()
                .body(service.saveOrUpdateList(dtos,id));
    }

    @GetMapping("/case/{id}")
    public ResponseEntity<List<PatientCaseAuthorizationDTO>> list(@PathVariable Long id) {
        List<PatientCaseAuthorizationDTO> list = service.getAll(id);
        return ResponseEntity.ok(list);
    }
}
