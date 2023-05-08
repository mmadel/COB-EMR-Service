package com.cob.emr.response;

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
            map.put("data", responseObj);
        map.put("time-stamp", new Date().getTime());

        return new ResponseEntity<>(map, status);
    }
}