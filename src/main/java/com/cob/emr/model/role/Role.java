package com.cob.emr.model.role;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Role {
    public static final String ADMIN_ROLE = "admin-role";
    public static final String PATIENT_ROLE = "emr-patient-role";
    public static final String[] values={"calendar-role","clinic-role","emr-patient-role","emr-referring-provider-role","finalize-medical-note-role","forward-medical-note-role","initialize-medical-note-role","insurance-company-role","medical-note-role","user-role","patient-payment-role"};
}
