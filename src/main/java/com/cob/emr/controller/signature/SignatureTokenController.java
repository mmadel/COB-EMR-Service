package com.cob.emr.controller.signature;

import com.cob.emr.exception.business.SignatureTokenException;
import com.cob.emr.model.signature.ConsentTextDto;
import com.cob.emr.model.signature.SendOtpRequest;
import com.cob.emr.model.signature.SignatureSubmitRequest;
import com.cob.emr.model.signature.VerifyOtpRequest;
import com.cob.emr.usecases.signature.SendSignatureOtpUseCase;
import com.cob.emr.usecases.signature.SubmitSignatureUseCase;
import com.cob.emr.usecases.signature.ValidateConsentUseCase;
import com.cob.emr.usecases.signature.ValidateSignatureTokenUseCase;
import com.cob.emr.usecases.signature.VerifySignatureOtpUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/signature")
public class SignatureTokenController {
    @Autowired
    ValidateSignatureTokenUseCase validateSignatureTokenUseCase;
    @Autowired
    SendSignatureOtpUseCase sendSignatureOtpUseCase;
    @Autowired
    VerifySignatureOtpUseCase verifySignatureOtpUseCase;
    @Autowired
    ValidateConsentUseCase validateConsentUseCase;
    @Autowired
    SubmitSignatureUseCase submitSignatureUseCase;

    @GetMapping("/validate-token/{token}")
    public ResponseEntity<Object> validateToken(@PathVariable("token") String token) throws SignatureTokenException {
        return ResponseEntity.ok()
                .body(validateSignatureTokenUseCase.validate(token));
    }

    @PostMapping("/send-otp")
    public ResponseEntity<Object> sendOtp(@RequestBody SendOtpRequest request) throws SignatureTokenException {
        sendSignatureOtpUseCase.send(request.getUuid());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/consent/current")
    public ResponseEntity<ConsentTextDto> getCurrentConsent() throws SignatureTokenException {
        return ResponseEntity.ok().body(validateConsentUseCase.getCurrentConsentText());
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Object> verifyOtp(@RequestBody VerifyOtpRequest request) throws SignatureTokenException {
        verifySignatureOtpUseCase.verify(request.getUuid(), request.getCode(), request.getConsent());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity<Object> submit(
            @RequestParam("token") String token,
            @RequestBody SignatureSubmitRequest request) throws SignatureTokenException {
        submitSignatureUseCase.submit(token, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
