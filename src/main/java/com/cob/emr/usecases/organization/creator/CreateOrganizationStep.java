package com.cob.emr.usecases.organization.creator;

import com.cob.emr.Utils.BeanFactory;
import com.cob.emr.entity.organization.Organization;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.repositories.organization.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class CreateOrganizationStep extends OrganizationCreationStepper {
    @Autowired
    ModelMapper mapper;
    @Autowired
    OrganizationRepository organizationRepository;
    @Override
    public boolean create(OrganizationModel model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Organization toBeCreated = mapper.map(model, Organization.class);
        toBeCreated.setClinics(null);
        Organization created = organizationRepository.save(toBeCreated);
        model.setId(created.getId());

        return nextStep(model);
    }
}
