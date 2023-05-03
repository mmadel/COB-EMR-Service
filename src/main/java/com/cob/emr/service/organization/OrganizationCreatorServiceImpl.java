package com.cob.emr.service.organization;

import com.cob.emr.model.organization.OrganizationModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrganizationCreatorServiceImpl implements OrganizationCreatorService {
    @Override
    public void create(OrganizationModel model) {

    }
}
