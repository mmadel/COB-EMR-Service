package com.cob.emr.service.organization.creator;

import com.cob.emr.model.organization.OrganizationModel;

public interface OrganizationCreatorService {

    OrganizationModel createOrUpdate(OrganizationModel model);

    void delete(Long organizationId);


}
