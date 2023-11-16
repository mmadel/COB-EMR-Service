package com.cob.emr.controller;

import com.cob.emr.exception.business.ClinicException;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.user.ClinicalUserModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.usecases.user.*;
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
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    CreateUserUseCase createUserUseCase;

    @Autowired
    FindUserByClinicIdentificationUseCase findUserByClinicIdentificationUseCase;
    @Autowired
    FindAllDoctorsUseCase findAllDoctorsUseCase;

    @Autowired
    DeleteUserUseCase deleteUserUseCase;

    @Autowired
    UpdateUserUseCase updateUserUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ClinicalUserModel model) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        createUserUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ClinicalUserModel model) {
        updateUserUseCase.update(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find/uuid/{uuid}")
    public ResponseEntity findUser(@PathVariable("uuid") String uuid) {
        return new ResponseEntity(findUserByClinicIdentificationUseCase.getUserByUUID(uuid), HttpStatus.OK);
    }

    @GetMapping("/find/clinicals/clinicId/{clinicId}")
    public ResponseEntity<Object> findClinicalUsers(@RequestParam(name = "offset") String offset,
                                                    @RequestParam(name = "limit") String limit,
                                                    @PathVariable("clinicId") Long clinicId) throws ClinicException {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully find all clinical users",
                        HttpStatus.OK, null,
                        findUserByClinicIdentificationUseCase.getUsers(paging, clinicId));
    }

    @GetMapping("/find/doctors/clinicId/{clinicId}")
    public ResponseEntity<Object> findAll(@RequestParam(name = "offset") String offset,
                                          @RequestParam(name = "limit") String limit,
                                          @PathVariable("clinicId") Long clinicId) throws ClinicException {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully find all doctors",
                        HttpStatus.OK, null,
                        findUserByClinicIdentificationUseCase.getDoctors(paging, clinicId));
    }

    @GetMapping("/find/doctors")
    public ResponseEntity findAllDoctors() {
        return new ResponseEntity(findAllDoctorsUseCase.find(), HttpStatus.OK);
    }
    @GetMapping("/find/all/doctors/clinicId/{clinicId}")
    public ResponseEntity findAllDoctors(@PathVariable("clinicId") Long clinicId) {
        return new ResponseEntity(findAllDoctorsUseCase.findByClinic(clinicId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity deleteUser(@PathVariable("uuid") String uuid) throws UserException {
        deleteUserUseCase.delete(uuid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/doctor/{uuid}")
    public ResponseEntity deleteDoctor(@PathVariable("uuid") String uuid) throws UserException {
        deleteUserUseCase.delete(uuid);
        return new ResponseEntity(HttpStatus.OK);
    }
}
