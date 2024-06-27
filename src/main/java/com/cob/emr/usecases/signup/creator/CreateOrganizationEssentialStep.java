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
        organization.setName(model.getEssential().getOrgname());
        organization.setDba(model.getEssential().getDba());
        organization.setTaxID(model.getEssential().getTaxID());
        organization.setGroupNPI(model.getEssential().getGroupNPI());
        AddressDependencyModel address = new AddressDependencyModel();
        address.setFirstAddress(model.getEssential().getFirstAddress());
        address.setSecondAddress(model.getEssential().getSecondAddress());
        address.setCountry(model.getEssential().getState());
        address.setCity(model.getEssential().getCity());
        address.setZipCode(model.getEssential().getZipCode());
        organization.setBillingAddress(address);
        return organization;
    }
}
