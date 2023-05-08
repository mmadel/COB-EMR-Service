package com.cob.emr.controller;

import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.clinic.ClinicFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/clinic")
public class ClinicController {

    @Autowired
    ClinicFinderService clinicFinderService;
    @GetMapping("/find/ids/{ids}")
    public ResponseEntity<Object> findByIds(@PathVariable("ids") List<Long> ids){
        return ResponseHandler
                .generateResponse("Successfully Find All Clinics By Ids",
                        HttpStatus.OK,
                        clinicFinderService.findByIds(ids));
    }
}
