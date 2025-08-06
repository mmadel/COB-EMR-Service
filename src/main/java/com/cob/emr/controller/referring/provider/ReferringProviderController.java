package com.cob.emr.controller.referring.provider;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.referring.provider.ReferringProviderModel;
import com.cob.emr.model.user.ClericalEMRUserModel;
import com.cob.emr.response.ResponseHandler;
import com.cob.emr.usecases.referring.provider.CreateReferringProviderUseCase;
import com.cob.emr.usecases.referring.provider.FindReferringProvidersUseCase;
import com.cob.emr.usecases.referring.provider.UpdateReferringProviderUseCase;
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
@RequestMapping(value = "/referring/provider")
public class ReferringProviderController {
    @Autowired
    CreateReferringProviderUseCase createReferringProviderUseCase;
    @Autowired
    FindReferringProvidersUseCase findReferringProvidersUseCase;
    @Autowired
    UpdateReferringProviderUseCase updateReferringProviderUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ReferringProviderModel model){
        createReferringProviderUseCase.create(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ReferringProviderModel model){
        updateReferringProviderUseCase.update(model);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/find/organizationId/{organizationId}")
    public ResponseEntity find(@RequestParam(name = "offset") String offset,
                               @RequestParam(name = "limit") String limit,
            @PathVariable Long organizationId){
        Pageable paging = PageRequest.of(Integer.parseInt(offset), Integer.parseInt(limit));
        return ResponseHandler
                .generateResponse("Successfully Find All Referring Providers!",
                        HttpStatus.OK, null,
                        findReferringProvidersUseCase.find(paging, organizationId));
    }
}
