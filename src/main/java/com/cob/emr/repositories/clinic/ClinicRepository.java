package com.cob.emr.repositories.clinic;

import com.cob.emr.entity.clinic.Clinic;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClinicRepository extends PagingAndSortingRepository<Clinic, Long> {
}
