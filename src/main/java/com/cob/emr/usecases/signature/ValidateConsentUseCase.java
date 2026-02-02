package com.cob.emr.usecases.signature;

import com.cob.emr.entity.signature.ConsentTextVersion;
import com.cob.emr.exception.business.SignatureTokenException;
import com.cob.emr.model.signature.ConsentAcknowledgmentDto;
import com.cob.emr.model.signature.ConsentTextDto;
import com.cob.emr.repositories.signature.ConsentTextVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidateConsentUseCase {
    @Autowired
    ConsentTextVersionRepository consentTextVersionRepository;

    public ConsentTextDto getCurrentConsentText() throws SignatureTokenException {
        Optional<ConsentTextVersion> optionalVersion =
                consentTextVersionRepository.findTopByActiveTrueOrderByEffectiveFromDesc();
        if (optionalVersion.isEmpty()) {
            throw new SignatureTokenException(HttpStatus.NOT_FOUND,
                    SignatureTokenException.CONSENT_VERSION_NOT_FOUND, null);
        }
        ConsentTextVersion version = optionalVersion.get();
        return ConsentTextDto.builder()
                .versionCode(version.getVersionCode())
                .title(version.getConsentTitle())
                .text(version.getConsentText())
                .textHash(version.getTextHash())
                .build();
    }

    public ConsentTextVersion validateConsentData(ConsentAcknowledgmentDto consentDto) throws SignatureTokenException {
        if (consentDto == null || !consentDto.isAcknowledged()) {
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.CONSENT_NOT_ACKNOWLEDGED, null);
        }

        Optional<ConsentTextVersion> optionalVersion =
                consentTextVersionRepository.findByVersionCode(consentDto.getTextVersion());
        if (optionalVersion.isEmpty() || !optionalVersion.get().isActive()) {
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.CONSENT_VERSION_NOT_FOUND, null);
        }

        ConsentTextVersion version = optionalVersion.get();
        if (!version.getTextHash().equals(consentDto.getTextHash())) {
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.CONSENT_TEXT_MISMATCH, null);
        }

        if (consentDto.getDisplayDurationMs() == null || consentDto.getDisplayDurationMs() < 3000) {
            throw new SignatureTokenException(HttpStatus.BAD_REQUEST,
                    SignatureTokenException.CONSENT_NOT_READ, null);
        }

        return version;
    }
}
