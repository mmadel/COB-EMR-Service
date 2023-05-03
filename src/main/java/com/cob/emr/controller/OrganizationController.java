package com.cob.emr.controller;

import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.service.organization.OrganizationCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void create(@RequestBody OrganizationModel model) {
        creatorService.create(model);
    }
}
