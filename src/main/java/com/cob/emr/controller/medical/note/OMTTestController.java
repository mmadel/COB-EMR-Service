package com.cob.emr.controller.medical.note;

import com.cob.emr.usecases.medical.note.test.UEQDServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/omt/test")
public class OMTTestController {
    @Autowired
    UEQDServiceTest ueqdServiceTest;

    @PostMapping("/ueqd")
    public ResponseEntity ueqdTest(@RequestBody Map<String, Integer> answers) {
        return ResponseEntity.ok()
                .body(ueqdServiceTest.test(answers));
    }
}
