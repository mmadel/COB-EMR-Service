CREATE TABLE IF NOT EXISTS consent_text_versions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    version_code VARCHAR(255) NOT NULL UNIQUE,
    effective_from DATETIME NOT NULL,
    effective_until DATETIME NULL,
    consent_title VARCHAR(255) NOT NULL,
    consent_text TEXT NOT NULL,
    text_hash VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at DATETIME,
    created_by VARCHAR(255)
);

INSERT INTO consent_text_versions (version_code, effective_from, consent_title, consent_text, text_hash, active, created_at, created_by)
VALUES (
    'V1.0',
    NOW(),
    'Electronic Signature Consent Agreement',
    CONCAT(
        '1. I consent to the use of an electronic signature to sign medical documents.\n\n',
        '2. I understand that my electronic signature has the same legal validity as a handwritten signature.\n\n',
        '3. I confirm that I am the authorized individual associated with this account and that no one else has access to my credentials.\n\n',
        '4. I acknowledge that all electronically signed documents will be stored securely and may be used as part of the official medical record.\n\n',
        '5. I understand that I may withdraw my consent to use electronic signatures at any time by contacting the system administrator.\n\n',
        '6. I agree that by completing the OTP verification process and acknowledging this consent, I am affixing my electronic signature to the associated document.'
    ),
    SHA2(CONCAT(
        '1. I consent to the use of an electronic signature to sign medical documents.\n\n',
        '2. I understand that my electronic signature has the same legal validity as a handwritten signature.\n\n',
        '3. I confirm that I am the authorized individual associated with this account and that no one else has access to my credentials.\n\n',
        '4. I acknowledge that all electronically signed documents will be stored securely and may be used as part of the official medical record.\n\n',
        '5. I understand that I may withdraw my consent to use electronic signatures at any time by contacting the system administrator.\n\n',
        '6. I agree that by completing the OTP verification process and acknowledging this consent, I am affixing my electronic signature to the associated document.'
    ), 256),
    TRUE,
    NOW(),
    'system'
);
