package com.cob.emr.controller.patient.record;

import com.cob.emr.model.patient.record.PatientRecordRequest;
import com.cob.emr.usecases.patient.record.FindPatientRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient/records")
public class PatientRecordsController {
    @Autowired
    FindPatientRecords findPatientRecords;

    @GetMapping("/find")
    public ResponseEntity getFieldBySection(@RequestBody PatientRecordRequest request, @RequestParam(name = "offset") int offset
            , @RequestParam(name = "limit") int limit) {
        return ResponseEntity.ok()
                .body(findPatientRecords.construct(request,offset + 1, limit));
    }

}
