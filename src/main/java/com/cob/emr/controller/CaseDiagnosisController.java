package com.cob.emr.controller;

import com.cob.emr.model.patient.cases.ICD10DiagnosisResponse;
import com.cob.emr.service.patient.cases.CaseDiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/case/diagnosis")
public class CaseDiagnosisController {

    @Autowired
    CaseDiagnosisService diagnosisService;
    @GetMapping(path = "/find/term/{term}")
    @ResponseBody
    public ResponseEntity retrieveDiagnosisByTerm(@PathVariable String term) {
        ICD10DiagnosisResponse diagnosis = null;
        try {
            diagnosis = diagnosisService.findByTerm(term);
        }catch (IOException e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(diagnosis);
    }
}
