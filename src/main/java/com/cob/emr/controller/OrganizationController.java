package com.cob.emr.controller;

import com.cob.emr.exception.business.OrganizationException;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.service.organization.creator.OrganizationCreatorService;
import com.cob.emr.service.organization.finder.OrganizationFinderService;
import com.cob.emr.usecases.organization.UpdateOrganizationUserCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "/organization")
public class OrganizationController {

    @Autowired
    OrganizationCreatorService creatorService;
    @Autowired
    OrganizationFinderService finderService;
    @Autowired
    UpdateOrganizationUserCase updateOrganizationUserCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody OrganizationModel model) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return ResponseHandler.generateResponse("Successfully added Organization", HttpStatus.OK, creatorService.createOrUpdate(model));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> update(@RequestBody OrganizationModel model) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, OrganizationException {
        return ResponseHandler.generateResponse("Successfully updated Organization", HttpStatus.OK, updateOrganizationUserCase.update(model));
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
//        creatorService.delete(id);
        return ResponseHandler.generateResponse("Successfully deleted Organization!", HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findAll() {
        return ResponseHandler
                .generateResponse("Successfully find all organization",
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
