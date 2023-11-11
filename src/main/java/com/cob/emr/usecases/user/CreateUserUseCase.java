package com.cob.emr.usecases.user;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.security.role.Role;
import com.cob.emr.entity.security.user.ClinicalUser;
import com.cob.emr.entity.security.user.DoctorUser;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.user.ClinicalUserModel;
import com.cob.emr.model.user.keycloak.KeyCloakUser;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.security.ClinicalUserRepository;
import com.cob.emr.repositories.security.DoctorUserRepository;
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
import java.util.*;
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
    DoctorUserRepository doctorUserRepository;
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
                .userName(model.getUserName())
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
        log.info("create user in EMR ", model.getUserName());
        ClinicalUser toBeCreated;
        if (model.getDoctor() != null) {
            log.info("User type is doctor ", model.getUserName());
            toBeCreated = mapper.map(model, DoctorUser.class);
            DoctorUser doctorToBeCreated = (DoctorUser) toBeCreated;
            fillDoctorData(model, doctorToBeCreated);
            constructUserEntity(doctorToBeCreated, model.getClinics(), model.getRole());
            doctorUserRepository.save(doctorToBeCreated);
        } else {
            log.info("User type is",model.getRole(), model.getUserName());
            toBeCreated = mapper.map(model, ClinicalUser.class);
            constructUserEntity(toBeCreated, model.getClinics(), model.getRole());
            clinicalUserRepository.save(toBeCreated);
        }
    }

    private void constructUserEntity(ClinicalUser user, List<String> clinicsIds, String role) {
        user.setClinics(new HashSet<>());
        user.setRoles(new HashSet<>());
        clinicsIds
                .stream()
                .map(clinicId -> clinicRepository.findById(Long.valueOf(clinicId)).get())
                .collect(Collectors.toSet())
                .forEach(clinic -> user.addClinic(clinic));
        Role userRole = roleRepository.findRoleByName(role);
        user.addRole(userRole);
    }

    private void fillDoctorData(ClinicalUserModel model, DoctorUser doctorToBeCreated) {
        doctorToBeCreated.setNpi(model.getDoctor().getNpi());
        doctorToBeCreated.setLicence(model.getDoctor().getLicence());
        doctorToBeCreated.setSpeciality(model.getDoctor().getSpeciality());
        doctorToBeCreated.setCredential(model.getDoctor().getCredential());
    }
}
