package com.cob.emr.controller.medical.note;

import com.cob.emr.usecases.medical.note.soap.UpdateSOAPChildNamesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medical/note/change/field-name")
public class MedicalNoteFieldNameController {

    @Autowired
    UpdateSOAPChildNamesUseCase updateSOAPChildNamesUseCase;
    @PostMapping("/change-child-names/parent/{uuid}")
    public ResponseEntity changeChildNames(@PathVariable String uuid) {
        return ResponseEntity.ok().body(updateSOAPChildNamesUseCase.update(uuid));
    }
}
