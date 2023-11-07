package com.cob.emr.controller;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.user.ClinicalUserModel;
import com.cob.emr.usecases.user.CreateUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ClinicalUserModel model) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        createUserUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }
}
