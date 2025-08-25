package com.cob.emr.controller.medical.note;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/omt/test")
public class OMTTestController {

    @PostMapping("/ueqd")
    public ResponseEntity createSection() {
        return ResponseEntity.ok()
                .build();
    }
}
