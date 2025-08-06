package com.cob.emr.controller.user;

import com.cob.emr.Utils.CookiesUtils;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.user.LoggedInUser;
import com.cob.emr.usecases.user.ConstructLoggedInUserUseCase;
import com.cob.emr.usecases.user.FindUserByUUIDUseCase;
import com.cob.emr.usecases.user.checking.CheckEmailUseCase;
import com.cob.emr.usecases.user.checking.CheckUserNameUseCase;
import com.cob.emr.usecases.user.keycloak.RemoveEMRRolesFromUserUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    CheckUserNameUseCase checkUserNameUseCase;
    @Autowired
    CheckEmailUseCase checkEmailUseCase;
    @Autowired
    FindUserByUUIDUseCase findUserByUUIDUseCase;
    @Autowired
    RemoveEMRRolesFromUserUseCase removeEMRRolesFromUserUseCase;
    @Autowired
    ConstructLoggedInUserUseCase constructLoggedInUserUseCase;
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/check/username/{username}")
    public ResponseEntity checkUserName(@PathVariable String username) throws UserException {

        return new ResponseEntity(checkUserNameUseCase.checkUserName(username), HttpStatus.OK);
    }

    @GetMapping("/check/email/{email}")
    public ResponseEntity checkEmail(@PathVariable String email) throws UserException {
        return new ResponseEntity(checkEmailUseCase.check(email), HttpStatus.OK);
    }

    @GetMapping("/find/uuid/{uuid}")
    public ResponseEntity findUser(@PathVariable String uuid) throws UserException {
        return new ResponseEntity(findUserByUUIDUseCase.find(uuid), HttpStatus.OK);
    }

    @GetMapping("/remove/roles/{uuid}")
    public ResponseEntity remove(@PathVariable String uuid) throws UserException {
        removeEMRRolesFromUserUseCase.removeAll(uuid);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/cache/logged-in/{uuid}")
    public ResponseEntity cacheLoggedInUser(@PathVariable String uuid) throws UserException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        LoggedInUser loggedInUser = constructLoggedInUserUseCase.construct(uuid);
        String loggedInUserAsString = objectMapper.writeValueAsString(loggedInUser);
        String encodedValueLoggedInUser = Base64Utils.encodeToUrlSafeString(loggedInUserAsString.getBytes());
        headers.add(HttpHeaders.SET_COOKIE, CookiesUtils.createCookie("user", encodedValueLoggedInUser, 86400).toString());
        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }
    @GetMapping("/get/logged-in")
    public ResponseEntity getLoggedInUser(@CookieValue(value = "user", defaultValue = "") String cookieValue) throws JsonProcessingException {
        String decodedValue = new String(Base64Utils.decodeFromUrlSafeString(cookieValue));
        return ResponseEntity.ok()
                .body(objectMapper.readValue(decodedValue, LoggedInUser.class));
    }
}
