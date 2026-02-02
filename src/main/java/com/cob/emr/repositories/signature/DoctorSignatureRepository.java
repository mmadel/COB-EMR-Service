package com.cob.emr.repositories.signature;

import com.cob.emr.entity.signature.DoctorSignature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorSignatureRepository extends JpaRepository<DoctorSignature, Long> {
}
