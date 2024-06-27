package com.cob.emr.usecases.signup;

import com.cob.emr.Utils.BeanFactory;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.organization.OrganizationRequest;
import com.cob.emr.usecases.signup.creator.CreateOrganizationClinicsStep;
import com.cob.emr.usecases.signup.creator.CreateOrganizationEssentialStep;
import com.cob.emr.usecases.signup.creator.OrganizationRequestCreator;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Component
public class SignUpOrganizationUseCase {
    public OrganizationRequest signup(OrganizationRequest model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        OrganizationRequestCreator organizationRequestCreator = OrganizationRequestCreator.link(
                BeanFactory.getBean(CreateOrganizationEssentialStep.class),
                BeanFactory.getBean(CreateOrganizationClinicsStep.class)
        );
        organizationRequestCreator.create(model);
        return model;
    }
}
