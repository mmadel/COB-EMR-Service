package com.cob.emr.response;

import com.cob.emr.model.response.AppointmentResponse;
import com.cob.emr.model.response.InsuranceCompanyResponse;
import com.cob.emr.model.response.PatientResponse;
import com.cob.emr.model.response.UserResponse;
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

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, PatientResponse response) {
        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, UserResponse response) {
        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), (int) response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, InsuranceCompanyResponse response) {

        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status,
                                                          Object responseObj, AppointmentResponse response) {

        Map<String, Object> map = populateResponseMap(message, status,
                response.getNumber_of_records(), response.getNumber_of_matching_records());
        map.put("records", response.getRecords());
        return new ResponseEntity<>(map, status);
    }


    private static Map<String, Object> populateResponseMap(String message, HttpStatus status,
                                                           Integer numberOfRecords, Integer numberOfMatchingRecords) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("number_of_records", numberOfRecords);
        map.put("number_of_matching_records", numberOfMatchingRecords);
        map.put("status", status.value());
        map.put("time-stamp", new Date().getTime());
        return map;
    }
}
