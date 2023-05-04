package com.cob.emr.controller;

import com.cob.emr.model.insurancecompnay.InsuranceCompanyModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.organization.insurance.company.InsuranceCompanyCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/insurance/company")
public class InsuranceCompanyController {

    @Autowired
    InsuranceCompanyCreatorService insuranceCompanyCreatorService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody InsuranceCompanyModel model) {
        return ResponseHandler.generateResponse("Successfully added Insurance Company!", HttpStatus.OK, insuranceCompanyCreatorService.createOrUpdate(model));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody InsuranceCompanyModel model) {
        return ResponseHandler.generateResponse("Successfully updated Insurance Company!", HttpStatus.OK, insuranceCompanyCreatorService.createOrUpdate(model));
    }

    @DeleteMapping("delete/id/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        insuranceCompanyCreatorService.delete(id);
        return ResponseHandler.generateResponse("Successfully deleted Insurance Company!", HttpStatus.OK, id);
    }
}
