package com.cob.emr.usecases.organization;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.organization.Organization;
import com.cob.emr.entity.security.user.DoctorUser;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.user.AdministratorDoctor;
import com.cob.emr.model.user.ClinicalUserModel;
import com.cob.emr.model.user.Doctor;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.security.DoctorUserRepository;
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
    static final String ADMINISTRATION_ROLE_NAME = "administration_emr_role";
    @Autowired
    ModelMapper mapper;
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    DoctorUserRepository doctorUserRepository;
    @Autowired
    CreateUserUseCase createUserUseCase;

    public void create(Organization created, List<ClinicModel> clinicModels) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        for (ClinicModel clinicModel : clinicModels) {
            Clinic clinic = mapper.map(clinicModel, Clinic.class);
            clinic.setOrganization(created);
            Long clinicId = clinicRepository.save(clinic).getId();
            persistAdministratorDoctor(clinicModel.getAdministratorDoctor(), clinicId.toString());
        }
    }

    private void persistAdministratorDoctor(AdministratorDoctor model, String clinicId) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (model.getId() != null) {
            DoctorUser doctorUser = doctorUserRepository.findById(model.getId()).get();
            Clinic clinic = clinicRepository.findById(Long.valueOf(clinicId)).get();
            clinic.getUsers().add(doctorUser);
        } else {
            ClinicalUserModel administratorDoctor = new ClinicalUserModel();
            administratorDoctor.setUserName(model.getUserName());
            administratorDoctor.setFirstName(model.getFirstName());
            administratorDoctor.setMiddleName(model.getMiddleName());
            administratorDoctor.setLastName(model.getLastName());
            administratorDoctor.setEmail(model.getEmail());
            administratorDoctor.setRole(ADMINISTRATION_ROLE_NAME);
            administratorDoctor.setPassword(model.getPassword());
            List<String> clinicIds = new ArrayList<>();
            clinicIds.add(clinicId);
            administratorDoctor.setClinics(clinicIds);
            Doctor doctorData = new Doctor();
            doctorData.setNpi(model.getNpi());
            administratorDoctor.setDoctor(doctorData);
            createUserUseCase.create(administratorDoctor);
        }
    }
}
