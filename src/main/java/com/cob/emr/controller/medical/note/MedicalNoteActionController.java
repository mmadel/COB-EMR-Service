package com.cob.emr.controller.medical.note;

import com.cob.emr.entity.medical.note.MedicalNote;
import com.cob.emr.exception.business.MedicalNoteException;
import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.usecases.medical.note.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medical/note/action")
public class MedicalNoteActionController {
    @Autowired
    CreateMedicalNoteUseCase createMedicalNoteUseCase;
    @Autowired
    RemoveMedicalNoteUseCase removeMedicalNoteUseCase;
    @Autowired
    FindMedicalNote findMedicalNote;
    @Autowired
    DraftMedicalNoteUseCase draftMedicalNoteUseCase;
    @Autowired
    CreateROMUseCase createROMUseCase;
    @Autowired
    FindROMUseCase findROMUseCase;
    @Autowired
    ForwardMedicalNotUseCase forwardMedicalNotUseCase;
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody MedicalNoteRequest request) throws MedicalNoteException {
        MedicalNote medicalNote = createMedicalNoteUseCase.createMedicalNote(request);
        return ResponseEntity.ok()
                .body(medicalNote.getId());

    }

    @PostMapping("/draft")
    public ResponseEntity draft(@RequestBody MedicalNoteRequest request) {
        draftMedicalNoteUseCase.draft(request);
        return ResponseEntity.ok()
                .build();

    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(removeMedicalNoteUseCase.remove(id));
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity find(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(findMedicalNote.find(id));
    }

    @PostMapping("/create/rom/name/{name}")
    public ResponseEntity createRangeOfMotion(@PathVariable String name, @RequestBody String htmlBody) throws JsonProcessingException {
        return ResponseEntity.ok()
                .body(createROMUseCase.create(name, htmlBody));
    }

    @GetMapping("/find/rom/name/{name}")
    public ResponseEntity findRangeOfMotionTest(@PathVariable String name) {
        return ResponseEntity.ok()
                .body(findROMUseCase.find(name));
    }

    @PutMapping("/forward/note/{note}/uuid/{uuid}")
    public ResponseEntity forward(@PathVariable Long note, @PathVariable String uuid) {
        forwardMedicalNotUseCase.forward(note,uuid);
        return ResponseEntity.ok()
                .build();
    }
}
