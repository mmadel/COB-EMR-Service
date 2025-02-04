package com.cob.emr.usecases.patient.cancellation.fee;

import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.PatientCancellationFee;
import com.cob.emr.model.patient.PatientCancellationFeeModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.cancellation.fee.PatientCancellationFeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateCancellationFeeUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientCancellationFeeRepository patientCancellationFeeRepository;
    @Autowired
    ClinicRepository clinicRepository;

    public void create(PatientCancellationFeeModel model) {
        Clinic clinic = clinicRepository
                .findById(model.getClinicId())
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found id: " + model.getClinicId()));
        PatientCancellationFee patientCancellationFee = mapper.map(model,PatientCancellationFee.class);
        patientCancellationFee.setClinic(clinic);
        patientCancellationFeeRepository.save(patientCancellationFee);
    }
}
