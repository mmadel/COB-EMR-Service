package com.cob.emr.usecases.organization;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.organization.Organization;
import com.cob.emr.model.clinic.ClinicDataHolder;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.organization.OrganizationModel;
import com.cob.emr.repositories.organization.OrganizationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class FindOrganizationUseCase {
    @Autowired
    OrganizationRepository repository;
    @Autowired
    ModelMapper mapper;

    public List<OrganizationModel> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(organization -> {
                    OrganizationModel model = mapper.map(organization, OrganizationModel.class);
                    model.setClinics(mapClinics(organization.getClinics()));
                    return model;
                })
                .collect(Collectors.toList());

    }

    private List<ClinicDataHolder> mapClinics(List<Clinic> clinics) {
        List<ClinicDataHolder> clinicDataHolders = new ArrayList<>();
        clinics.stream()
                .forEach(clinic -> {
                    ClinicDataHolder clinicDataHolder = new ClinicDataHolder();
                    ClinicModel clinicModel = mapper.map(clinic, ClinicModel.class);
                    clinicDataHolder.setClinicModel(clinicModel);
                    clinicDataHolders.add(clinicDataHolder);
                });
        return clinicDataHolders;
    }
}
