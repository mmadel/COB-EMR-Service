package com.cob.emr.controller.user;

import com.cob.emr.exception.business.ClinicException;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.user.ClericalEMRUserModel;
import com.cob.emr.model.user.ClinicalEMRUserModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.usecases.user.clerical.CreateClericalUserUseCase;
import com.cob.emr.usecases.user.clerical.FindClericalUsersByClinic;
import com.cob.emr.usecases.user.keycloak.CreateKeyCloakUserUseCase;
import com.cob.emr.usecases.user.keycloak.creator.CreateUserCredentialsStep;
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
@RequestMapping(value = "/clerical/user")
public class ClericalUserController {
    @Autowired
    CreateClericalUserUseCase createClericalUserUseCase;
    @Autowired
    FindClericalUsersByClinic findClericalUsersByClinic;
    @Autowired
    CreateKeyCloakUserUseCase createKeyCloakUserUseCase;

    @PostMapping("/create")
    public ResponseEntity createClericalUser(@RequestBody ClericalEMRUserModel model) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        createKeyCloakUserUseCase.create(model);
        createClericalUserUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find/clinicId/{clinicId}")
    public ResponseEntity<Object> findAll(@RequestParam(name = "offset") String offset,
                                          @RequestParam(name = "limit") String limit,
                                          @PathVariable("clinicId") Long clinicId) throws ClinicException {
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully find clerical users",
                        HttpStatus.OK, null,
                        findClericalUsersByClinic.find(paging, clinicId));
    }

    @GetMapping("/find/clinicId/{clinicId}/uuid/{uuid}")
    public ResponseEntity<Object> find(@PathVariable Long clinicId, @PathVariable String uuid) throws  UserException {
        return ResponseHandler
                .generateResponse("Successfully find clerical users",
                        HttpStatus.OK, findClericalUsersByClinic.find(clinicId, uuid)
                );
    }
}
