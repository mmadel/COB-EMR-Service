package com.cob.emr.controller.medical.note;

import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.usecases.medical.note.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody MedicalNoteRequest request) {
        createMedicalNoteUseCase.createMedicalNote(request);
        return ResponseEntity.ok()
                .build();

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

    @GetMapping("/find/id/{id}/type/{type}")
    public ResponseEntity find(@PathVariable Long id, @PathVariable String type) {
        String decodedType = URLDecoder.decode(type, StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .body(findMedicalNote.find(id, type));
    }
    @PostMapping("/create/rom/name/{name}")
    public ResponseEntity createRangeOfMotion(@PathVariable String name,@RequestBody String htmlBody) throws JsonProcessingException {
        return ResponseEntity.ok()
                .body(createROMUseCase.create(name,htmlBody));
    }
}
