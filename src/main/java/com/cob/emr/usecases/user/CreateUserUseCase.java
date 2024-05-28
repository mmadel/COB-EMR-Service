package com.cob.emr.usecases.user;


import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.user.ClinicalEMRUserModel;
import com.cob.emr.model.user.keycloak.KeyCloakUser;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
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
import java.util.stream.Collectors;

@Component
@Slf4j
public class CreateUserUseCase {
    @Autowired
    KeyCloakUserCreationService keyCloakUserCreationService;
    @Autowired
    KeyCloakUserCreationService keyCloakUsersCreatorService;
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;

    public void create(ClinicalEMRUserModel administratorDoctor) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //createKeyCloakUser(administratorDoctor);
        createEMRUser(administratorDoctor);
    }

    private void createKeyCloakUser(ClinicalEMRUserModel model) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        log.info("create user in keycloak ", model.getAccountName());
        KeyCloakUser keyCloakUser = KeyCloakUser.builder()
                .userName(model.getAccountName())
//                .firstName(model.getFirstName())
//                .lastName(model.getLastName())
                .email(model.getEmail())
//                .password(model.getPassword())
//                .roles(Arrays.asList(model.getRole()))
                .build();
        String uuid = keyCloakUsersCreatorService.create(keyCloakUser).getId();
        model.setUuid(uuid);
    }

    private void createEMRUser(ClinicalEMRUserModel model) {
        log.info("create user in EMR ", model.getAccountName());
        ClinicalEMRUser toBeCreated = mapper.map(model, ClinicalEMRUser.class);

        model.getClinicsId()
                .stream()
                .map(clinicId -> clinicRepository.findById(clinicId).get())
                .collect(Collectors.toSet())
                .forEach(clinic -> toBeCreated.addClinic(clinic));

        clinicalEMRUserRepository.save(toBeCreated);
    }
}
