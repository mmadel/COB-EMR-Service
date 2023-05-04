package com.cob.emr.controller;

import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.organization.OrganizationCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/organization")
public class OrganizationController {

    @Autowired
    OrganizationCreatorService creatorService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody OrganizationModel model) {
        return ResponseHandler.generateResponse("Successfully added Organization!", HttpStatus.OK, creatorService.create(model));
    }
}
