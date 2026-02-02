package com.cob.emr.repositories.signature;

import com.cob.emr.entity.signature.DoctorSignatureToken;
import com.cob.emr.entity.signature.SignatureCaptureMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignatureCaptureMetadataRepository extends JpaRepository<SignatureCaptureMetadata, Long> {
    Optional<SignatureCaptureMetadata> findBySignatureToken(DoctorSignatureToken signatureToken);

    @Modifying
    void deleteBySignatureToken(DoctorSignatureToken signatureToken);
}
