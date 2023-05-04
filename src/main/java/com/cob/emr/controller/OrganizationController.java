package com.cob.emr.controller;

import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.organization.creator.OrganizationCreatorService;
import com.cob.emr.service.organization.finder.OrganizationFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/organization")
public class OrganizationController {

    @Autowired
    OrganizationCreatorService creatorService;
    @Autowired
    OrganizationFinderService finderService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody OrganizationModel model) {
        return ResponseHandler.generateResponse("Successfully added Organization!", HttpStatus.OK, creatorService.createOrUpdate(model));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody OrganizationModel model) {
        return ResponseHandler.generateResponse("Successfully updated Organization!", HttpStatus.OK, creatorService.createOrUpdate(model));
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        creatorService.delete(id);
        return ResponseHandler.generateResponse("Successfully deleted Organization!", HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findAll() {
        return ResponseHandler
                .generateResponse("Successfully deleted Organization!",
                        HttpStatus.OK,
                        finderService.findAll());
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        return ResponseHandler
                .generateResponse("Successfully deleted Organization!",
                        HttpStatus.OK,
                        finderService.findById(id));
    }
}
