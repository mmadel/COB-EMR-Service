package com.cob.emr.controller.medical.note;

import com.cob.emr.model.medical.note.omt.test.BalanceRequest;
import com.cob.emr.model.medical.note.omt.test.LowerExtremityRequest;
import com.cob.emr.model.medical.note.omt.test.OMTTestValues;
import com.cob.emr.model.medical.note.omt.test.SPADIRequest;
import com.cob.emr.usecases.medical.note.test.*;
import com.cob.emr.usecases.medical.note.test.balance.BalanceServiceTest;
import com.cob.emr.usecases.medical.note.test.lower.extremity.LowerExtremityServiceTest;
import com.cob.emr.usecases.medical.note.test.spine.SpineServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/omt/test")
public class OMTTestController {
    @Autowired
    UEQDServiceTest ueqdServiceTest;
    @Autowired
    UEFIServiceTest uefiServiceTest;
    @Autowired
    SPADIServiceTest spadiServiceTest;
    @Autowired
    DASHServiceTest dashServiceTest;
    @Autowired
    SpineServiceTest spineServiceTest;
    @Autowired
    LowerExtremityServiceTest lowerExtremityServiceTest;
    @Autowired
    BalanceServiceTest BalanceRequest;
    @Autowired
    SaveOMTTestValues saveOMTTestValues;
    @Autowired
    FindMedicalNoteTestValues findMedicalNoteTestValues;

    @PostMapping("/ueqd")
    public ResponseEntity ueqdTest(@RequestBody Map<String, Integer> answers) {
        return ResponseEntity.ok()
                .body(ueqdServiceTest.test(answers));
    }

    @PostMapping("/uefi")
    public ResponseEntity uefiTest(@RequestBody Map<String, Integer> answers) {
        return ResponseEntity.ok()
                .body(uefiServiceTest.test(answers));
    }

    @PostMapping("/spadi")
    public ResponseEntity spadiTest(@RequestBody SPADIRequest spadiRequest) {
        return ResponseEntity.ok()
                .body(spadiServiceTest.test(spadiRequest));
    }

    @PostMapping("/dash")
    public ResponseEntity dashTest(@RequestBody Map<String, Integer> answers) {
        return ResponseEntity.ok()
                .body(dashServiceTest.test(answers));
    }

    @PostMapping("/spine/test/{testName}")
    public ResponseEntity spineTest(@RequestBody Map<String, Integer> answers, @PathVariable String testName) {
        return ResponseEntity.ok()
                .body(spineServiceTest.test(answers, testName));
    }

    @PostMapping("/lower-extremity/test/{testName}")
    public ResponseEntity LowerExtremityTest(@RequestBody LowerExtremityRequest lowerExtremityRequest, @PathVariable String testName) {
        return ResponseEntity.ok()
                .body(lowerExtremityServiceTest.execute(testName, lowerExtremityRequest));
    }

    @PostMapping("/balance")
    public ResponseEntity balanceTest(@RequestBody BalanceRequest balanceRequest) {
        return ResponseEntity.ok()
                .body(BalanceRequest.test(balanceRequest));
    }

    @PostMapping("/save/values")
    public ResponseEntity saveValues(@RequestBody OMTTestValues omtTestValues) {
        saveOMTTestValues.save(omtTestValues);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/values/medical-note-id/{medicalNoteId}/test-name/{testName}")
    public ResponseEntity getValues(@PathVariable Long medicalNoteId, @PathVariable String testName) {
        return ResponseEntity.ok()
                .body(findMedicalNoteTestValues.find(medicalNoteId, testName));
    }
}
