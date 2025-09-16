package com.cob.emr.repositories.patient.authorization;

import com.cob.emr.entity.patient.authorization.PatientCaseAuthorization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientCaseAuthorizationRepository extends CrudRepository<PatientCaseAuthorization, Long> {

}
