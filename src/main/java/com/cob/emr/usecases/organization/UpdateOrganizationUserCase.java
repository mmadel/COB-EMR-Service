package com.cob.emr.usecases.organization;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.exception.business.OrganizationException;
import com.cob.emr.exception.business.UserException;
import com.cob.emr.exception.business.UserKeyCloakException;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.organization.OrganizationRepository;
import com.cob.emr.usecases.clinic.DeleteClinicUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateOrganizationUserCase {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    DeleteClinicUseCase deleteClinicUseCase;
    @Autowired
    CreateOrganizationClinicsUseCase createOrganizationClinicsUseCase;

    @Transactional(rollbackOn = {UserKeyCloakException.class, UserException.class})
    public Long update(OrganizationModel model) throws OrganizationException, UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        updateOrganizationBasicInformation(model);
        Organization organization = find(model.getId());
        List<ClinicModel> fromDatabase = new ArrayList<>();
        organization.getClinics()
                .forEach(clinic -> {
                    ClinicModel clinicModel = new ClinicModel();
                    clinicModel.setId(clinic.getId());
                    clinicModel.setName(clinic.getName());
                    clinicModel.setAddress(clinic.getAddress());
                    clinicModel.setOrganizationId(clinic.getOrganization().getId());
                });
        deleteRemovedClinics(organization, fromDatabase, model.getClinics());
        addNewClinics(organization, model.getClinics(), fromDatabase);
        return null;

    }

    private void updateOrganizationBasicInformation(OrganizationModel model) {
        Organization toBeUpdated = mapper.map(model, Organization.class);
        organizationRepository.save(toBeUpdated);
    }

    private Organization find(long organizationId) throws OrganizationException {
        Organization returnedOrganization = organizationRepository.findById(organizationId).get();
        if (returnedOrganization == null) {
            throw new OrganizationException(OrganizationException.ORGANIZATION_NOT_FOUND);
        }
        return returnedOrganization;
    }

    private void deleteRemovedClinics(Organization organization, List<ClinicModel> fromDatabase, List<ClinicModel> fromUser) {
        List<ClinicModel> clinicModels = findClinicsDifference(fromDatabase, fromUser);
        clinicModels.stream().forEach(clinicModel -> {
            deleteClinicUseCase.deleteByOrganization(clinicModel.getId(), organization.getId());
        });
    }

    private void addNewClinics(Organization organization, List<ClinicModel> fromUser, List<ClinicModel> fromDatabase) throws UserKeyCloakException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, UserException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        List<ClinicModel> clinicModels = findClinicsDifference(fromUser, fromDatabase);
        createOrganizationClinicsUseCase.create(organization, clinicModels);
    }

    private List<ClinicModel> findClinicsDifference(List<ClinicModel> first, List<ClinicModel> second) {
        List<ClinicModel> difference = new ArrayList<>();
        for (ClinicModel clinicModel : first) {
            if (!second.contains(clinicModel)) {
                difference.add(clinicModel);
            }
        }
        return difference;
    }
}
