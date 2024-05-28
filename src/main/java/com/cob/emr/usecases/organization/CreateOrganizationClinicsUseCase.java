package com.cob.emr.usecases.organization;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.organization.Organization;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.clinic.ClinicDataHolder;
import com.cob.emr.model.user.ClinicalEMRUserModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import com.cob.emr.usecases.user.CreateUserUseCase;
import org.modelmapper.ModelMapper;
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
public class CreateOrganizationClinicsUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;
    @Autowired
    CreateUserUseCase createUserUseCase;

    public void create(Organization created, List<ClinicDataHolder> clinics) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        for (ClinicDataHolder clinicDataHolder : clinics) {
            Clinic clinic = mapper.map(clinicDataHolder.getClinicModel(), Clinic.class);
            clinic.setOrganization(created);
            Long clinicId = clinicRepository.save(clinic).getId();
            persistAdministratorDoctor(clinicDataHolder.getAdministratorDoctor(), clinicId);
        }
    }

    private void persistAdministratorDoctor(ClinicalEMRUserModel model, Long clinicId) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (model.getId() != null) {
            ClinicalEMRUser clinicalEMRUser = clinicalEMRUserRepository.findById(model.getId()).get();
            Clinic clinic = clinicRepository.findById(Long.valueOf(clinicId)).get();
            clinic.getClinicalUsers().add(clinicalEMRUser);
        } else {
            ClinicalEMRUserModel administratorDoctor = new ClinicalEMRUserModel();
            administratorDoctor.setAccountName(model.getAccountName());
            administratorDoctor.setNpi(model.getNpi());
            administratorDoctor.setSpeciality(model.getSpeciality());
            administratorDoctor.setLicence(model.getLicence());
            administratorDoctor.setCredential(model.getCredential());
            administratorDoctor.setUuid(model.getUuid());
            administratorDoctor.setEmail(model.getEmail());
            List<Long> clinicIds = new ArrayList<>();
            clinicIds.add(clinicId);
            administratorDoctor.setClinicsId(clinicIds);
            createUserUseCase.create(administratorDoctor);
        }
    }
}
