package com.cob.emr.service.clinic;

import com.cob.emr.model.clinic.ClinicModel;

import java.util.List;

public interface ClinicFinderService {

    List<ClinicModel> findByIds(List<Long> ids);
}
