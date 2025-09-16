package com.cob.emr.repositories.patient.authorization;

import com.cob.emr.entity.patient.authorization.PatientCaseAuthorization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientCaseAuthorizationRepository extends CrudRepository<PatientCaseAuthorization, Long> {
    Optional<List<PatientCaseAuthorization>> findByPatientCase_Id(Long id);

}
