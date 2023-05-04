package com.cob.emr.repositories.organization;

import com.cob.emr.entity.organization.Organization;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long> {
}
