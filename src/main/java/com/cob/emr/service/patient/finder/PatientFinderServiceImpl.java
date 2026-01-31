package com.cob.emr.service.patient.finder;

import com.cob.emr.Utils.PatientUtil;
import com.cob.emr.entity.clinic.Clinic;
import com.cob.emr.entity.patient.Patient;
import com.cob.emr.entity.patient.authorization.PatientCaseAuthorization;
import com.cob.emr.entity.security.user.ClinicalEMRUser;
import com.cob.emr.exception.business.PatientException;
import com.cob.emr.model.clinic.ClinicModel;
import com.cob.emr.model.patient.PatientModel;
import com.cob.emr.model.patient.cases.PatientCaseAuthorizationData;
import com.cob.emr.model.patient.cases.PatientCaseModel;
import com.cob.emr.model.patient.cases.TreatingDoctorModel;
import com.cob.emr.model.response.PatientResponse;
import com.cob.emr.repositories.clinic.ClinicRepository;
import com.cob.emr.repositories.medical.note.MedicalNoteRepository;
import com.cob.emr.repositories.patient.PatientRepository;
import com.cob.emr.repositories.patient.authorization.PatientCaseAuthorizationRepository;
import com.cob.emr.repositories.patient.cases.PatientCaseRepository;
import com.cob.emr.usecases.patient.cases.FindPatientCasesUseCase;
import com.cob.emr.usecases.patient.record.AssignPatientCaseRecordActionsUseCase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientFinderServiceImpl implements PatientFinderService {

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    PatientCaseRepository patientCaseRepository;

    @Autowired
    ModelMapper mapper;
    @Autowired
    FindPatientCasesUseCase findPatientCasesUseCase;
    @Autowired
    MedicalNoteRepository medicalNoteRepository;
    @Autowired
    AssignPatientCaseRecordActionsUseCase assignPatientCaseRecordActionsUseCase;
    @Autowired
    PatientCaseAuthorizationRepository patientCaseAuthorizationRepository;

    @Override
    public PatientResponse findAll(Pageable pageable, Long clinicId) {

        List<Clinic> clinics = new ArrayList<>();
        clinics.add(clinicRepository
                .findById(clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Clinic with id not found" + clinicId)));
        Page<Patient> pages = patientRepository.findByClinicsIn(pageable, clinics);
        long total = (pages).getTotalElements();
        List<PatientModel> records = pages.stream().map(patient -> {
                    PatientModel model = mapper.map(patient, PatientModel.class);
                    model.setPatientStatus(patient.getPatientStatus());
                    model.setEmrId(patient.getEmrId());
                    model.setClinicsId(patient.getClinics()
                            .stream()
                            .map(clinic -> clinic.getId()).collect(Collectors.toList()));
                    return model;
                })
                .collect(Collectors.toList());
        return PatientResponse.builder()
                .number_of_records(records.size())
                .number_of_matching_records((int) total)
                .records(records)
                .build();

    }

    @Override
    public List<PatientModel> finsAll(Long clinicId) {
        Clinic clinic = clinicRepository
                .findById(clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Clinic with id not found" + clinicId));
        return patientRepository.findByClinic(clinic).stream()
                .map(patient -> {
                    PatientModel model = mapper.map(patient, PatientModel.class);
                    model.setFullName(PatientUtil.constructPatientName(model.getFirstName()
                            , model.getMiddleName()
                            , model.getLastName()));
                    model.setCases(findPatientCasesUseCase.find(patient.getId()));
                    List<ClinicModel> clinicModels = patient.getClinics().stream()
                            .map(clinicEntity -> mapper.map(clinicEntity, ClinicModel.class))
                            .collect(Collectors.toList());
                    model.setClinicModels(clinicModels);
                    return model;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PatientModel findById(Long clinicId, Long patientId) throws PatientException {
        List<Clinic> clinics = new ArrayList<>();
        clinics.add(clinicRepository
                .findById(clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Clinic with id not found" + clinicId)));
        Patient queriedPatient = patientRepository
                .findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Clinic with id not found" + patientId));

        Optional<Patient> patient = patientRepository.findByIdAndClinicsIn(patientId, clinics).stream().findFirst();
        if (patient.isPresent()) {
            PatientModel model = mapper.map(patient.get(), PatientModel.class);
            model.setPatientStatus(patient.get().getPatientStatus());
            model.setEmrId(patient.get().getEmrId());
            model.setCases(patientCaseRepository
                    .findByPatient_Id(patientId)
                    .stream()
                    .map(patientCase -> {
                        PatientCaseModel caseModel = mapper.map(patientCase, PatientCaseModel.class);
                        List<String> actions = assignPatientCaseRecordActionsUseCase.assign(patientCase.getId());
                        caseModel.setPatientRecordActions(actions);
                        PatientCaseAuthorizationData authorizationData = populatePatientCaseAuthorization(patientCase.getId(), patientCase.getAuthorizationCounter());
                        caseModel.setAuthorizationData(authorizationData);
                        caseModel.setUuid(patientCase.getUuid());
                        fillPatientTherapist(patientCase.getClinicalEMRUser(),caseModel);
                        return caseModel;
                    })
                    .collect(Collectors.toList()));
            List<Patient> patients = new ArrayList<>();
            patients.add(queriedPatient);
            model.setClinicsId(
                    clinicRepository.findByPatientsIn(patients).stream().map(Clinic::getId).collect(Collectors.toList())
            );
            return model;
        } else {
            throw new PatientException(HttpStatus.CONFLICT, PatientException.PATIENT_NOTFOUND, new Object[]{patientId, clinicId});
        }
    }

    private PatientCaseAuthorizationData populatePatientCaseAuthorization(Long caseId, Long counter) {
        Optional<PatientCaseAuthorization> authorization = patientCaseAuthorizationRepository.findByPatientCase_Id(caseId)
                .orElseThrow(() -> new IllegalArgumentException(" PatientCaseAuthorization entity not found for case : " + caseId))
                .stream().findFirst();
        if (authorization.isPresent()) {
            return PatientCaseAuthorizationData.builder()
                    .counter(counter)
                    .numberAuthorized(authorization.get().getAuthNumber())
                    .effectiveEndtDate(authorization.get().getEffectiveEnd())
                    .build();
        } else
            return null;
    }
    private void fillPatientTherapist(ClinicalEMRUser clinicalEMRUser , PatientCaseModel patientCaseModel){
        TreatingDoctorModel treatingDoctorModel = new TreatingDoctorModel();
        treatingDoctorModel.setFirstName(clinicalEMRUser.getFirstName());
        treatingDoctorModel.setMiddleName(clinicalEMRUser.getMiddleName());
        treatingDoctorModel.setLastName(clinicalEMRUser.getLastName());
        treatingDoctorModel.setNPI(clinicalEMRUser.getNpi());
        patientCaseModel.setTreatingDoctor(treatingDoctorModel);
    }
}
