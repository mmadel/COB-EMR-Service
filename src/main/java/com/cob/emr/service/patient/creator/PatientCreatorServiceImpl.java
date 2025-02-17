package com.cob.emr.service.patient.creator;

import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.PatientCase;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.repositories.security.ClinicalEMRUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class PatientCreatorServiceImpl implements PatientCreatorService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientCaseRepository patientCaseRepository;

    @Autowired
    ClinicRepository clinicRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    ClinicalEMRUserRepository clinicalEMRUserRepository;

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
        Patient patient = mapper.map(model, Patient.class);
        StreamSupport
                .stream(clinicRepository.findAllById(model.getClinicsId()).spliterator(), false)
                .forEach(clinic -> clinic.addPatient(patient));

        persistPatientCases(patient, model.getCases());
        patientRepository.save(patient);
        return mapper.map(patient, PatientModel.class);

    }

    private void persistPatientCases(Patient patient, List<PatientCaseModel> models) {
        Set<PatientCase> patientCases = new HashSet<>();
        models.forEach(patientCaseModel -> {
            PatientCase patientCase = mapper.map(patientCaseModel, PatientCase.class);
            ClinicalEMRUser clinicalEMRUser = clinicalEMRUserRepository.findById(patientCaseModel.getTherapist()).get();
            patientCase.setClinicalEMRUser(clinicalEMRUser);
            patientCase.setPatient(patient);
            patientCases.add(patientCase);
        });
        patient.setCases(patientCases);
    }
}
