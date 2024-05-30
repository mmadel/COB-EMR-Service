package com.cob.emr.usecases.organization.creator;

import com.cob.emr.Utils.BeanFactory;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.clinic.ClinicDataHolder;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.usecases.user.keycloak.CreateKeyCloakUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class CreateKeyCloakUserStep extends OrganizationCreationStepper {
    @Autowired
    CreateKeyCloakUserUseCase createKeyCloakUserUseCase;
    @Override
    public boolean create(OrganizationModel model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        for (ClinicDataHolder holder : model.getClinics()) {
            createKeyCloakUserUseCase.create(holder.getAdministratorDoctor());
        }
        return nextStep(model);
    }
}
