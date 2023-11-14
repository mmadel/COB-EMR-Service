package com.cob.emr.repositories.security;

import com.cob.emr.entity.security.resource.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
