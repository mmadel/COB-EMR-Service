package com.cob.emr.usecases.patient.cancellation.fee;

import com.cob.emr.entity.patient.PatientCancellationFee;
import com.cob.emr.model.patient.PatientCancellationFeeModel;
import com.cob.emr.repositories.patient.cancellation.fee.PatientCancellationFeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindCancellationFeeUseCase {
    @Autowired
    ModelMapper mapper;
    @Autowired
    PatientCancellationFeeRepository patientCancellationFeeRepository;
    public PatientCancellationFeeModel find(Long clinicId){
        Optional<PatientCancellationFee> optional= patientCancellationFeeRepository.findByClinic_Id(clinicId);
        if(optional.isPresent()){
            PatientCancellationFeeModel model=mapper.map(optional.get(),PatientCancellationFeeModel.class);
            model.setClinicId(clinicId);
            return model;
        }

        else
            return new PatientCancellationFeeModel();
    }
}
