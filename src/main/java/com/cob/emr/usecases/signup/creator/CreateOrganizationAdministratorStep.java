package com.cob.emr.usecases.signup.creator;

import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.organization.OrganizationClinicsInformation;
import com.cob.emr.model.organization.OrganizationRequest;
import com.cob.emr.model.user.ClericalEMRUserModel;
import com.cob.emr.model.user.ClinicalEMRUserModel;
import com.cob.emr.model.user.EMRUser;
import com.cob.emr.usecases.signup.util.ConstructAdministratorRoles;
import com.cob.emr.usecases.user.clerical.CreateClericalUserUseCase;
import com.cob.emr.usecases.user.clinical.CreateClinicalUserUseCase;
import com.cob.emr.usecases.user.keycloak.CreateKeyCloakUserUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateOrganizationAdministratorStep extends OrganizationRequestCreator {
    @Autowired
    CreateKeyCloakUserUseCase createKeyCloakUserUseCase;
    @Autowired
    CreateClinicalUserUseCase createClinicalUserUseCase;
    @Autowired
    CreateClericalUserUseCase createClericalUserUseCase;
    @Autowired
    ModelMapper mapper;

    @Override
    public boolean create(OrganizationRequest model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        ConstructAdministratorRoles.construct(model.getAdministrator());
        createKeyCloakUserUseCase.create(model.getAdministrator());
        switch (model.getAdministrator().getUserType()) {
            case Clinical:
                ClinicalEMRUserModel clinicalEMRUser = mapper.map(model.getAdministrator(),ClinicalEMRUserModel.class);
                assignClinicsToClinicalUsers(clinicalEMRUser, model.getClinics());
                createClinicalUserUseCase.create(clinicalEMRUser);
                break;
            case Clerical:
                ClericalEMRUserModel clericalEMRUser = mapper.map(model.getAdministrator(),ClericalEMRUserModel.class);
                assignClinicsToClericalUsers(clericalEMRUser, model.getClinics());
                createClericalUserUseCase.create(clericalEMRUser);
                break;
        }
        return nextStep(model);
    }

    private void assignClinicsToClinicalUsers(ClinicalEMRUserModel clinicalEMRUser, List<OrganizationClinicsInformation> clinics) {
        List<Long> ids = clinics.stream()
                .map(organizationClinicsInformation -> organizationClinicsInformation.getId())
                .collect(Collectors.toList());
        clinicalEMRUser.setClinicIds(ids);
    }

    private void assignClinicsToClericalUsers(ClericalEMRUserModel clericalEMRUser, List<OrganizationClinicsInformation> clinics) {
        List<Long> ids = clinics.stream()
                .map(organizationClinicsInformation -> organizationClinicsInformation.getId())
                .collect(Collectors.toList());
        clericalEMRUser.setClinicIds(ids);
    }
}
