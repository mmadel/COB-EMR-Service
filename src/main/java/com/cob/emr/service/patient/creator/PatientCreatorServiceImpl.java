package com.cob.emr.service.patient.creator;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.entity.patient.PatientInsurance;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.model.patient.insurance.PatientInsuranceModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.repositories.patient.insurance.PatientInsuranceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class PatientCreatorServiceImpl implements PatientCreatorService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientInsuranceRepository patientInsuranceRepository;

    @Autowired
    PatientCaseRepository patientCaseRepository;

    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;

    @Override
    public PatientModel create(PatientModel model) {
        /*
         * Save Patient
         * Save Insurance's patient with reference to saved patient
         * Save Case's patient  with reference to saved patient and patient insurances
         * */
        return persistPatient(model);
    }


    private PatientModel persistPatient(PatientModel model) {
        Patient toBeCreated = mapper.map(model, Patient.class);
        StreamSupport
                .stream(clinicRepository.findAllById(model.getClinicsId()).spliterator(), false)
                .forEach(clinic -> clinic.addPatient(toBeCreated));
        Patient created = patientRepository.save(toBeCreated);

        persistPatientCases(created, model.getPatientCaseModels());

        persistPatientInsurances(created , model.getPatientInsuranceModels());

        return mapper.map(created, PatientModel.class);
    }

    private void persistPatientInsurances(Patient created, List<PatientInsuranceModel> models) {
        models.forEach(patientInsuranceModel -> {
            PatientInsurance patientInsurance = mapper.map(patientInsuranceModel, PatientInsurance.class);
            patientInsurance.setPatient(created);
            patientInsuranceRepository.save(patientInsurance);
        });
    }

    private void persistPatientCases(Patient created, List<PatientCaseModel> models) {
        models.forEach(patientCaseModel -> {
            PatientCase patientCase = mapper.map(patientCaseModel, PatientCase.class);
            patientCase.setPatient(created);
            patientCaseRepository.save(patientCase);
        });
    }
}