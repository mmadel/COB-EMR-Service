package com.cob.emr.service.organization.creator;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.repositories.organization.OrganizationRepository;
import com.cob.emr.usecases.organization.CreateOrganizationClinicsUseCase;
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
