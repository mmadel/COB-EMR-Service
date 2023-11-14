package com.cob.emr.service.organization.creator;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.organization.Organization;
import com.cob.emr.entity.security.user.DoctorUser;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.model.user.AdministratorDoctor;
import com.cob.emr.model.user.ClinicalUserModel;
import com.cob.emr.model.user.Doctor;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.organization.OrganizationRepository;
import com.cob.emr.repositories.security.DoctorUserRepository;
import com.cob.emr.usecases.organization.CreateOrganizationClinicsUseCase;
import com.cob.emr.usecases.user.CreateUserUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrganizationCreatorServiceImpl implements OrganizationCreatorService {

    @Autowired
    OrganizationRepository repository;
    @Autowired
    ModelMapper mapper;

    @Autowired
    CreateOrganizationClinicsUseCase createOrganizationClinicsUseCase;

    @Transactional(rollbackOn = {UserKeyCloakException.class, UserException.class})
    @Override
    public OrganizationModel createOrUpdate(OrganizationModel model) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Organization toBeCreated = mapper.map(model, Organization.class);
        toBeCreated.setClinics(null);
        Organization created = repository.save(toBeCreated);
        createOrganizationClinicsUseCase.create(created, model.getClinics());
        return mapper.map(created, OrganizationModel.class);
    }
}
