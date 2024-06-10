package com.cob.emr.repositories.referring.provider;

import com.cob.emr.entity.referring.provider.ReferringProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReferringProviderRepository extends PagingAndSortingRepository<ReferringProvider, Long> {
    Page<ReferringProvider> findByOrganization_Id(Pageable pageable, Long id);
}
