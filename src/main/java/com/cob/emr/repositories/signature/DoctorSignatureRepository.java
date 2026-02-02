package com.cob.emr.repositories.signature;

import com.cob.emr.entity.signature.DoctorSignature;
import com.cob.emr.entity.signature.DoctorSignatureToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorSignatureRepository extends JpaRepository<DoctorSignature, Long> {
    @Modifying
    void deleteBySignatureToken(DoctorSignatureToken signatureToken);
}
