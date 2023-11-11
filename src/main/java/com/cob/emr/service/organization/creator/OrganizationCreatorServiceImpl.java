package com.cob.emr.service.organization.creator;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.organization.Organization;
import com.cob.emr.entity.security.user.DoctorUser;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.model.user.AdministratorDoctor;
import com.cob.emr.model.user.ClinicalUserModel;
import com.cob.emr.model.user.Doctor;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.organization.OrganizationRepository;
import com.cob.emr.repositories.security.DoctorUserRepository;
import com.cob.emr.usecases.user.CreateUserUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrganizationCreatorServiceImpl implements OrganizationCreatorService {
    static final String ADMINISTRATION_ROLE_NAME = "administration_emr_role";
    @Autowired
    OrganizationRepository repository;

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    DoctorUserRepository doctorUserRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    CreateUserUseCase createUserUseCase;


    @Transactional(rollbackOn = {UserKeyCloakException.class, UserException.class})
    @Override
    public OrganizationModel createOrUpdate(OrganizationModel model) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Organization toBeCreated = mapper.map(model, Organization.class);
        toBeCreated.setClinics(null);
        Organization created = repository.save(toBeCreated);
        String[][] cachedClinics = new String[model.getClinics().size()][2];
        persistClinic(created, model.getClinics(), cachedClinics);
        return mapper.map(created, OrganizationModel.class);
    }

    @Override
    public void delete(Long organizationId) {
        Organization toBeDeleted = repository
                .findById(organizationId)
                .orElseThrow(() -> new IllegalArgumentException(organizationId.toString()));

        clinicRepository.deleteAllById(
                toBeDeleted.getClinics()
                        .stream()
                        .map(Clinic::getId)
                        .collect(Collectors.toList()));
        repository.delete(toBeDeleted);
    }

    private void persistClinic(Organization created, List<ClinicModel> clinicModels, String[][] cachedClinics) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
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
            //clinicRepository.save(clinic);
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
