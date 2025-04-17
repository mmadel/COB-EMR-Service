package com.cob.emr.controller.user;

import com.cob.emr.exception.business.ClinicException;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.user.ClinicalEMRUserModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.usecases.user.clinical.*;
import com.cob.emr.usecases.user.keycloak.CreateKeyCloakUserUseCase;
import com.cob.emr.usecases.user.keycloak.DeleteKeyCloakUserUseCase;
import com.cob.emr.usecases.user.keycloak.UpdateKeyCloakUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(value = "/clinical/user")
public class ClinicalUserController {

    @Autowired
    CreateClinicalUserUseCase createClinicalUserUseCase;

    @Autowired
    FindClinicalUsersByClinicUseCase findClinicalUsersByClinicUseCase;

    @Autowired
    CreateKeyCloakUserUseCase createKeyCloakUserUseCase;
    @Autowired
    UpdateKeyCloakUserUseCase updateKeyCloakUserUseCase;
    @Autowired
    DeleteKeyCloakUserUseCase deleteKeyCloakUserUseCase;
    @Autowired
    DeleteClinicalUserUseCase deleteClinicalUserUseCase;
    @Autowired
    UpdateClinicalUserUseCase updateClinicalUserUseCase;
    @Autowired
    FindClinicalUserWithFinalizationAuthUSeCase findClinicalUserWithFinalizationAuthUSeCase;

    @PostMapping("/create")
    public ResponseEntity createClinicalUser(@RequestBody ClinicalEMRUserModel model) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        createKeyCloakUserUseCase.create(model);
        createClinicalUserUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity updateClinicalUser(@RequestBody ClinicalEMRUserModel model) {
        updateKeyCloakUserUseCase.update(model);
        updateClinicalUserUseCase.update(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/uuid/{uuid}")
    public ResponseEntity deleteClinicalUser(@PathVariable("uuid") String uuid) throws UserException {
        deleteKeyCloakUserUseCase.delete(uuid);
        deleteClinicalUserUseCase.delete(uuid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find/clinicId/{clinicId}")
    public ResponseEntity<Object> findClinicalUsers(@RequestParam(name = "offset") String offset,
                                                    @RequestParam(name = "limit") String limit,
                                                    @PathVariable("clinicId") Long clinicId) throws ClinicException {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully find all clinical users",
                        HttpStatus.OK, null,
                        findClinicalUsersByClinicUseCase.find(paging, clinicId));
    }

    @GetMapping("/find/uuid/{uuid}")
    public ResponseEntity<Object> find(@PathVariable String uuid) throws UserException {
        return new ResponseEntity<>(findClinicalUsersByClinicUseCase.find(uuid), HttpStatus.OK);
    }

    @GetMapping("/find-all/clinicId/{clinicId}")
    public ResponseEntity<Object> find(@PathVariable Long clinicId) throws UserException {
        return new ResponseEntity<>(findClinicalUsersByClinicUseCase.find(clinicId), HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Object> find() throws UserException {
        return new ResponseEntity<>(findClinicalUsersByClinicUseCase.find(), HttpStatus.OK);
    }

    @GetMapping("/find/finalize/note/clinic/{clinic}/user/{user}")
    public ResponseEntity findAuthProviderToFinalize(@PathVariable Long clinic, @PathVariable String user) throws UserException {
        return ResponseEntity.ok()
                .body(findClinicalUserWithFinalizationAuthUSeCase.find(clinic,user));
    }

}
