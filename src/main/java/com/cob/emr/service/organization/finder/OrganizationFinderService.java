package com.cob.emr.service.organization.finder;

import com.cob.emr.model.organization.OrganizationModel;

import java.util.List;

public interface OrganizationFinderService {
    List<OrganizationModel> findAll();

    OrganizationModel findById(Long id);
}
