package com.cob.emr.usecases.organization.creator;

import com.cob.emr.Utils.BeanFactory;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.organization.Organization;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.model.clinic.ClinicDataHolder;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import org.checkerframework.checker.units.qual.A;
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
public class CreateClinicsStep extends OrganizationCreationStepper {
    @Autowired
    ModelMapper mapper;
    @Autowired
    ClinicRepository clinicRepository;

    @Override
    public boolean create(OrganizationModel model) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        System.out.println("CreateClinicsStep");
        for (ClinicDataHolder holder : model.getClinics()) {
            Clinic toBeCreated = mapper.map(holder.getClinicModel(), Clinic.class);
            Organization organization = mapper.map(model , Organization.class);
            toBeCreated.setOrganization(organization);
            Long clinicId = clinicRepository.save(toBeCreated).getId();
            holder.getClinicModel().setId(clinicId);
            holder.getAdministratorDoctor().getClinicsId().add(clinicId);
        }
        return nextStep(model);
    }
}
