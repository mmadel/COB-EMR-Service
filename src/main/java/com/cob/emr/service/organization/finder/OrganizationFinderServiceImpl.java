package com.cob.emr.service.organization.finder;

import com.cob.emr.entity.organization.Organization;
import com.cob.emr.model.clinic.ClinicDataHolder;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.repositories.organization.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        OrganizationModel organizationModel = mapper.map(organization, OrganizationModel.class);
        List<ClinicDataHolder> clinics = new ArrayList<>();
        organization.getClinics()
                .forEach(clinic -> {
                    ClinicDataHolder clinicDataHolder = new ClinicDataHolder();
                    clinicDataHolder.setClinicModel(mapper.map(clinic , ClinicModel.class));
                    clinics.add(clinicDataHolder);
                });
        organizationModel.setClinics(clinics);
        return organizationModel;
    }
}
