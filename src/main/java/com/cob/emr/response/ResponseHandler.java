package com.cob.emr.response;

import com.cob.emr.model.response.PatientResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status) {
        return generateResponse(message, status, null);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        if (responseObj != null)
            map.put("records", responseObj);
        map.put("time-stamp", new Date().getTime());

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,Object responseObj, PatientResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("number_of_records", response.getNumber_of_records());
        map.put("number_of_matching_records", response.getNumber_of_matching_records());
        map.put("status", status.value());
        map.put("records", response.getRecords());
        map.put("time-stamp", new Date().getTime());

        return new ResponseEntity<>(map, status);

    }
}
