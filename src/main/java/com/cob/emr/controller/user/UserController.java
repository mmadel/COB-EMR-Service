package com.cob.emr.controller.user;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.usecases.user.FindUserByUUIDUseCase;
import com.cob.emr.usecases.user.checking.CheckEmailUseCase;
import com.cob.emr.usecases.user.checking.CheckUserNameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    CheckUserNameUseCase checkUserNameUseCase;
    @Autowired
    CheckEmailUseCase checkEmailUseCase;
    @Autowired
    FindUserByUUIDUseCase findUserByUUIDUseCase;

    @GetMapping("/check/username/{username}")
    public ResponseEntity checkUserName(@PathVariable String username) throws UserException {

        return new ResponseEntity(checkUserNameUseCase.checkUserName(username), HttpStatus.OK);
    }

    @GetMapping("/check/email/{email}")
    public ResponseEntity checkEmail(@PathVariable String email) throws UserException {
        return new ResponseEntity(checkEmailUseCase.check(email),HttpStatus.OK);
    }

    @GetMapping("/find/uuid/{uuid}")
    public ResponseEntity findUser(@PathVariable String uuid) throws UserException {
        return new ResponseEntity(findUserByUUIDUseCase.find(uuid),HttpStatus.OK);
    }

}
