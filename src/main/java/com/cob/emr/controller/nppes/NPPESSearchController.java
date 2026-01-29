package com.cob.emr.controller.nppes;

import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.nppes.NPPESSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/nppes")
public class NPPESSearchController {

    @Autowired
    private NPPESSearchService nppesSearchService;

    @GetMapping("/search/first-name/{firstName}")
    public ResponseEntity<Object> searchByFirstName(@PathVariable String firstName) {
        return ResponseHandler.generateResponse("Successfully retrieved NPPES results",
                HttpStatus.OK,
                nppesSearchService.searchByFirstName(firstName));
    }

    @GetMapping("/search/last-name/{lastName}")
    public ResponseEntity<Object> searchByLastName(@PathVariable String lastName) {
        return ResponseHandler.generateResponse("Successfully retrieved NPPES results",
                HttpStatus.OK,
                nppesSearchService.searchByLastName(lastName));
    }

    @GetMapping("/search/first-name/{firstName}/last-name/{lastName}")
    public ResponseEntity<Object> searchByFirstAndLastName(@PathVariable String firstName,
                                                           @PathVariable String lastName) {
        return ResponseHandler.generateResponse("Successfully retrieved NPPES results",
                HttpStatus.OK,
                nppesSearchService.searchByFirstAndLastName(firstName, lastName));
    }

    @GetMapping("/search/npi/{npi}")
    public ResponseEntity<Object> searchByNPI(@PathVariable String npi) {
        return ResponseHandler.generateResponse("Successfully retrieved NPPES results",
                HttpStatus.OK,
                nppesSearchService.searchByNPI(npi));
    }
}
