package com.cob.emr.controller.user;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.usecases.user.ConstructLoggedInUserUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/loggedIn/user")
public class LoggedInUSerController {
    @Autowired
    ConstructLoggedInUserUseCase constructLoggedInUserUseCase;
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/cache/{uuid}")
    public ResponseEntity cacheLoggedInUser(@PathVariable String uuid) throws UserException, JsonProcessingException {
        return ResponseEntity.ok()
                .body(constructLoggedInUserUseCase.construct(uuid));
    }

}
