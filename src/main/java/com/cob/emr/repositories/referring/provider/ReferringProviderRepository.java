package com.cob.emr.repositories.referring.provider;

import com.cob.emr.entity.referring.provider.ReferringProvider;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReferringProviderRepository extends CrudRepository<ReferringProvider,Long> {
    Optional<List<ReferringProvider>> findByOrganization_Id(Long id);
}
