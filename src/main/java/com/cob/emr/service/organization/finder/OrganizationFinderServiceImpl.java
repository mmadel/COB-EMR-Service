package com.cob.emr.service.organization.finder;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.organization.Organization;
import com.cob.emr.entity.security.user.ClinicalUser;
import com.cob.emr.entity.security.user.DoctorUser;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.model.user.AdministratorDoctor;
import com.cob.emr.repositories.organization.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationFinderServiceImpl implements OrganizationFinderService {
    static final String ADMINISTRATION_ROLE = "administration_emr_role";
    @Autowired
    OrganizationRepository repository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<OrganizationModel> findAll() {
        List<OrganizationModel> result = new ArrayList<>();
        repository.findAll()
                .forEach(organization ->
                        result.add(mapper.map(organization, OrganizationModel.class)));
        return result;
    }

    @Override
    public OrganizationModel findById(Long id) {
        Organization organization = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id.toString()));
        Map<Clinic, ClinicalUser> clinicClinicalUserMap = new HashMap<>();
        organization.getClinics()
                .forEach(clinic -> {
                    for (ClinicalUser user : clinic.getUsers()) {
                        if (user.getRoles().stream().findFirst().get().getName().equals(ADMINISTRATION_ROLE)) {
                            clinicClinicalUserMap.put(clinic, user);
                            break;
                        }
                    }
                });
        OrganizationModel organizationModel = mapper.map(organization, OrganizationModel.class);
        List<ClinicModel> clinics = new ArrayList<>();
        clinicClinicalUserMap.forEach((clinic, clinicalUser) -> {
            ClinicModel clinicModel = mapper.map(clinic, ClinicModel.class);
            AdministratorDoctor administratorDoctor = new AdministratorDoctor();
            administratorDoctor.setId(clinicalUser.getId());
            administratorDoctor.setUserName(clinicalUser.getUserName());
            administratorDoctor.setFirstName(clinicalUser.getFirstName());
            administratorDoctor.setMiddleName(clinicalUser.getMiddleName());
            administratorDoctor.setLastName(clinicalUser.getLastName());
            administratorDoctor.setEmail(clinicalUser.getEmail());
            administratorDoctor.setNpi(((DoctorUser) clinicalUser).getNpi());
            administratorDoctor.setNpi(((DoctorUser) clinicalUser).getNpi());
            clinicModel.setAdministratorDoctor(administratorDoctor);
            clinics.add(clinicModel);
        });
        organizationModel.setClinics(clinics);
        return organizationModel;
    }
}
