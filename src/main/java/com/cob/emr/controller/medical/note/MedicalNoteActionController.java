package com.cob.emr.controller.medical.note;

import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.usecases.medical.note.CreateMedicalNoteUseCase;
import com.cob.emr.usecases.medical.note.RemoveMedicalNoteUseCase;
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

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody MedicalNoteRequest request) {
        createMedicalNoteUseCase.createMedicalNote(request);
        return ResponseEntity.ok()
                .build();

    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        removeMedicalNoteUseCase.remove(id);
        return ResponseEntity.ok()
                .body(id);
    }
}
