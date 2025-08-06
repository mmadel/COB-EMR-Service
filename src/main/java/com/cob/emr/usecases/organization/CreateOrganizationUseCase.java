package com.cob.emr.usecases.organization;

import com.cob.emr.Utils.BeanFactory;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.usecases.organization.creator.*;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class CreateOrganizationUseCase {
    public OrganizationModel create(OrganizationModel model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        OrganizationCreationStepper organizationCreationStepper = OrganizationCreationStepper.link(
                BeanFactory.getBean(CreateOrganizationStep.class),
                BeanFactory.getBean(CreateClinicsStep.class),
                BeanFactory.getBean(CreateKeyCloakUserStep.class),
                BeanFactory.getBean(CreateClinicalUserStep.class)

        );
        organizationCreationStepper.create(model);
        return model;
    }
}
