package com.cob.emr.usecases.signup.creator;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.organization.Organization;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.common.AddressDependencyModel;
import com.cob.emr.model.organization.OrganizationRequest;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.organization.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CreateOrganizationClinicsStep extends OrganizationRequestCreator {
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    OrganizationRepository organizationRepository;

    @Override
    public boolean create(OrganizationRequest model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        List<Clinic> clinics = new ArrayList<>();
        model.getClinics().forEach(clinicInformation -> {
            Clinic clinic = new Clinic();
            clinic.setName(clinicInformation.getName());
            clinic.setAddress(buildAddress(clinicInformation.getAddress()));
            Organization organization = organizationRepository.findById(model.getId()).get();
            clinic.setOrganization(organization);
            clinics.add(clinic);
        });
        clinicRepository.saveAll(clinics);
        return nextStep(model);
    }

    private AddressDependencyModel buildAddress(String addressStr) {
        AddressDependencyModel model = new AddressDependencyModel();
        String[] add = addressStr.split(",");
        model.setFirstAddress(add[0]);
        if (add.length == 4) {
            model.setState(add[1]);
            model.setCity(add[2]);
            model.setZipCode(add[3]);
        } else {
            model.setSecondAddress(add[1]);
            model.setState(add[2]);
            model.setCity(add[3]);
            model.setZipCode(add[4]);
        }
        return model;
    }
}
