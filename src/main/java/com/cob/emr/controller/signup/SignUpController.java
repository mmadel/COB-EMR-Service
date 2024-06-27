package com.cob.emr.controller.signup;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.organization.OrganizationRequest;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.usecases.signup.SignUpOrganizationUseCase;
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
@RequestMapping(value = "/request")
public class SignUpController {
    @Autowired
    SignUpOrganizationUseCase signUpOrganizationUseCase;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody OrganizationRequest model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return ResponseHandler.generateResponse("Successfully added Organization", HttpStatus.OK, signUpOrganizationUseCase.signup(model));
    }
}
