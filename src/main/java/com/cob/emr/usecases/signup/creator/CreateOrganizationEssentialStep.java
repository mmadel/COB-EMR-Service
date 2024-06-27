package com.cob.emr.usecases.signup.creator;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.common.AddressDependencyModel;
import com.cob.emr.model.organization.OrganizationRequest;
import com.cob.emr.repositories.organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class CreateOrganizationEssentialStep extends OrganizationRequestCreator {
    @Autowired
    OrganizationRepository organizationRepository;

    @Override
    public boolean create(OrganizationRequest model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Organization organization = fillEntity(model);
        Long id = organizationRepository.save(organization).getId();
        model.setId(id);
        return nextStep(model);
    }

    private Organization fillEntity(OrganizationRequest model) {
        Organization organization = new Organization();
        organization.setName(model.getOrgname());
        organization.setDba(model.getDba());
        organization.setTaxID(model.getTaxID());
        organization.setGroupNPI(model.getGroupNPI());
        AddressDependencyModel address = new AddressDependencyModel();
        address.setFirstAddress(model.getFirstAddress());
        address.setSecondAddress(model.getSecondAddress());
        address.setCountry(model.getState());
        address.setCity(model.getCity());
        address.setZipCode(model.getZipCode());
        organization.setBillingAddress(address);
        return organization;
    }
}
