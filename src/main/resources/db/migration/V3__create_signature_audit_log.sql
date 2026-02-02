CREATE TABLE signature_audit_log (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id   BIGINT       NULL,
    token_id    BIGINT       NULL,
    event_type  VARCHAR(255) NOT NULL,
    success     BOOLEAN      NOT NULL,
    error_code  VARCHAR(30)  NULL,
    ip_address  VARCHAR(45)  NULL,
    user_agent  VARCHAR(1024) NULL,
    details     JSON         NULL,
    performed_by VARCHAR(255) NULL,
    performed_at BIGINT      NOT NULL
);

CREATE INDEX idx_sig_audit_doctor ON signature_audit_log (doctor_id, event_type, performed_at);
CREATE INDEX idx_sig_audit_token  ON signature_audit_log (token_id, performed_at);
CREATE INDEX idx_sig_audit_ip     ON signature_audit_log (ip_address, performed_at);
