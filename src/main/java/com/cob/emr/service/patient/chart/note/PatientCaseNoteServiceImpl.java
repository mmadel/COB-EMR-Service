package com.cob.emr.service.patient.chart.note;

import com.cob.emr.entity.patient.chart.note.PatientChartNote;
import com.cob.emr.model.patient.chart.note.PatientChartNoteRequest;
import com.cob.emr.repositories.patient.chart.note.PatientChartNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void update(PatientChartNoteRequest patientChartNoteRequest) {
        PatientChartNote patientChartNote = patientChartNoteRepository
                .findByNoteId(patientChartNoteRequest.getNoteId())
                .orElseGet(() -> toEntity(patientChartNoteRequest));
        patientChartNote.setNoteStatus(patientChartNoteRequest.getNoteStatus());
        patientChartNote.setNoteId(patientChartNoteRequest.getNoteId());
        patientChartNote.setPatientCaseUuid(patientChartNoteRequest.getPatientCaseUuid());
        patientChartNote.setNoteType(patientChartNoteRequest.getNoteType());
        patientChartNote.setDos(patientChartNoteRequest.getDos());
        patientChartNoteRepository.save(patientChartNote);
    }

    @Override
    @Transactional
    public void delete(String noteId) {
        patientChartNoteRepository.findByNoteId(noteId)
                .ifPresent(patientChartNoteRepository::delete);
    }

    private PatientChartNote toEntity(PatientChartNoteRequest patientChartNoteRequest) {
        PatientChartNote patientChartNote = new PatientChartNote();
        patientChartNote.setPatientCaseUuid(patientChartNoteRequest.getPatientCaseUuid());
        patientChartNote.setNoteId(patientChartNoteRequest.getNoteId());
        patientChartNote.setNoteStatus(patientChartNoteRequest.getNoteStatus());
        patientChartNote.setNoteType(patientChartNoteRequest.getNoteType());
        patientChartNote.setEncounterDate(patientChartNoteRequest.getEncounterDate());
        patientChartNote.setDos(patientChartNoteRequest.getDos());
        return patientChartNote;
    }
}
