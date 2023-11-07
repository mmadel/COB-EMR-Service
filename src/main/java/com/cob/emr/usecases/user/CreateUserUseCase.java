package com.cob.emr.usecases.user;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.role.Role;
import com.cob.emr.entity.security.user.ClinicalUser;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.user.ClinicalUserModel;
import com.cob.emr.model.user.keycloak.KeyCloakUser;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.security.ClinicalUserRepository;
import com.cob.emr.repositories.security.RoleRepository;
import com.cob.emr.usecases.user.keycloak.KeyCloakUserCreationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CreateUserUseCase {
    @Autowired
    KeyCloakUserCreationService keyCloakUserCreationService;
    @Autowired
    KeyCloakUserCreationService keyCloakUsersCreatorService;
    @Autowired
    ClinicalUserRepository clinicalUserRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;

    public void create(ClinicalUserModel model) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        createKeyCloakUser(model);
        createEMRUser(model);
    }

    private void createKeyCloakUser(ClinicalUserModel model) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        log.info("create user in keycloak ", model.getUserName());
        KeyCloakUser keyCloakUser = KeyCloakUser.builder()
                .username(model.getUserName())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .email(model.getEmail())
                .password(model.getPassword())
                .roles(Arrays.asList(model.getRole()))
                .build();
        String uuid = keyCloakUsersCreatorService.create(keyCloakUser).getId();
        model.setUuid(uuid);
    }

    private void createEMRUser(ClinicalUserModel model) {
        ClinicalUser toBeCreated = mapper.map(model, ClinicalUser.class);
        toBeCreated.setClinics(new HashSet<>());
        toBeCreated.setRoles(new HashSet<>());
        model.getClinics()
                .stream()
                .map(clinicModel -> clinicRepository.findById(clinicModel.getId()).get())
                .collect(Collectors.toSet())
                .forEach(clinic -> toBeCreated.addClinic(clinic));
        Role userRole = roleRepository.findRoleByName(model.getRole());
        toBeCreated.addRole(userRole);
        clinicalUserRepository.save(toBeCreated);
    }
}
