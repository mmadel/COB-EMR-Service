package com.cob.emr.service.patient.chart.note;

import com.cob.emr.entity.patient.chart.note.PatientChartNote;
import com.cob.emr.model.patient.chart.note.PatientChartNoteRequest;
import com.cob.emr.repositories.patient.chart.note.PatientChartNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientCaseNoteServiceImpl implements PatientCaseNoteService {
    private final PatientChartNoteRepository patientChartNoteRepository;

    @Override
    public void create(PatientChartNoteRequest patientChartNoteRequest) {
        PatientChartNote tobeSaved = toEntity(patientChartNoteRequest);
        patientChartNoteRepository.save(tobeSaved);
    }

    @Override
    public void update(PatientChartNoteRequest patientChartNoteRequest) {
        patientChartNoteRepository.findByNoteId(patientChartNoteRequest.getNoteId())
                .ifPresent(patientChartNote -> {
                    patientChartNote.setNoteStatus(patientChartNoteRequest.getNoteStatus());
                    patientChartNoteRepository.save(patientChartNote);
                });
    }

    private PatientChartNote toEntity(PatientChartNoteRequest patientChartNoteRequest) {
        PatientChartNote patientChartNote = new PatientChartNote();
        patientChartNote.setPatientId(patientChartNoteRequest.getPatientId());
        patientChartNote.setPatientCaseId(patientChartNoteRequest.getPatientCaseId());
        patientChartNote.setNoteId(patientChartNoteRequest.getNoteId());
        patientChartNote.setNoteStatus(patientChartNoteRequest.getNoteStatus());
        patientChartNote.setNoteType(patientChartNoteRequest.getNoteType());
        patientChartNote.setEncounterDate(patientChartNoteRequest.getEncounterDate());
        return patientChartNote;
    }
}
