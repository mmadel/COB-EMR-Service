package com.cob.emr.controller.medical.note;

import com.cob.emr.model.medical.note.MedicalNoteRequest;
import com.cob.emr.usecases.medical.note.CreateMedicalNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medical/note/action")
public class MedicalNoteActionController {
    @Autowired
    CreateMedicalNoteUseCase createMedicalNoteUseCase;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody MedicalNoteRequest request){
        createMedicalNoteUseCase.createMedicalNote(request);
        return ResponseEntity.ok()
                .build();

    }
}
