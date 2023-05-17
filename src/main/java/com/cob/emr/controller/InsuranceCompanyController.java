package com.cob.emr.controller;

import com.cob.emr.model.insurancecompnay.InsuranceCompanyModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.insurance.company.creator.InsuranceCompanyCreatorService;
import com.cob.emr.service.insurance.company.finder.InsuranceCompanyFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/insurance/company")
public class InsuranceCompanyController {

    @Autowired
    InsuranceCompanyCreatorService insuranceCompanyCreatorService;
    @Autowired
    InsuranceCompanyFinderService insuranceCompanyFinderService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody InsuranceCompanyModel model) {
        return ResponseHandler.
                generateResponse("Successfully added Insurance Company!",
                        HttpStatus.OK,
                        insuranceCompanyCreatorService.createOrUpdate(model));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody InsuranceCompanyModel model) {
        return ResponseHandler.
                generateResponse("Successfully updated Insurance Company!",
                        HttpStatus.OK,
                        insuranceCompanyCreatorService.createOrUpdate(model));
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        insuranceCompanyCreatorService.delete(id);
        return ResponseHandler.
                generateResponse("Successfully deleted Insurance Company!",
                        HttpStatus.OK, id);
    }

    @GetMapping("/find/clinicId/{clinicId}")
    public ResponseEntity<Object> findAll(@RequestParam(name = "offset") String offset,
                                          @RequestParam(name = "limit") String limit,
            @PathVariable("clinicId") Long clinicId) {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully Find All Insurance Companies!",
                        HttpStatus.OK,null,
                        insuranceCompanyFinderService.findAll(paging, clinicId));
    }

    @GetMapping("/find/clinicId/{clinicId}/company/{companyId}")
    public ResponseEntity<Object> findById(@PathVariable("companyId") Long companyId,
                                           @PathVariable("clinicId") Long clinicId) {
        return ResponseHandler
                .generateResponse("Successfully Find All Insurance Companies!",
                        HttpStatus.OK,
                        insuranceCompanyFinderService.findById(companyId, clinicId));
    }
}
