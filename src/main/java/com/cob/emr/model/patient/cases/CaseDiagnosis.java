package com.cob.emr.model.patient.cases;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
    public class CaseDiagnosis {
    private String diagnosisCode;
    private String diagnosisDescription;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CaseDiagnosis)) return false;
        CaseDiagnosis that = (CaseDiagnosis) o;
        return diagnosisCode != null && diagnosisCode.equalsIgnoreCase(that.diagnosisCode);
    }

    @Override
    public int hashCode() {
        return diagnosisCode != null ? diagnosisCode.toLowerCase().hashCode() : 0;
    }
}
