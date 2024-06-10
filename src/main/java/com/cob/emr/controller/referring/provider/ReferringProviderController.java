package com.cob.emr.controller.referring.provider;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.referring.provider.ReferringProviderModel;
import com.cob.emr.model.user.ClericalEMRUserModel;
import com.cob.emr.usecases.referring.provider.CreateReferringProviderUseCase;
import com.cob.emr.usecases.referring.provider.FindReferringProvidersUseCase;
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
@RequestMapping(value = "/referring/provider")
public class ReferringProviderController {
    @Autowired
    CreateReferringProviderUseCase createReferringProviderUseCase;
    @Autowired
    FindReferringProvidersUseCase findReferringProvidersUseCase;
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ReferringProviderModel model) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        createReferringProviderUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find/organizationId/{organizationId}")
    public ResponseEntity find(@PathVariable Long organizationId){
        return new ResponseEntity(findReferringProvidersUseCase.find(organizationId),HttpStatus.OK);
    }
}
