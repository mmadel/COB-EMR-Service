package com.cob.emr.model.response.patient.record;

import com.cob.emr.model.patient.record.PatientRecord;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class PatientRecordResponse {
    Integer number_of_records;
    Integer number_of_matching_records;
    List<PatientRecord> records;
}
