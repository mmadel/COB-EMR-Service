package com.cob.emr.controller.medical.note;

import com.cob.emr.service.mapper.CsvToYamlService;
import com.cob.emr.service.mapper.FieldMappingService;
import com.cob.emr.usecases.medical.note.post.init.note.CreateInitialExaminationScoreUseCase;
import com.cob.emr.usecases.medical.note.post.init.note.ReadActiveOMTTestUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;

@RestController
@RequestMapping("/map/field/test")
public class TestController {
    @Autowired
    FieldMappingService fieldMappingService;
    @Autowired
    CsvToYamlService service;
    @Autowired
    ReadActiveOMTTestUseCase readActiveOMTTestUseCase;
    @Autowired
    CreateInitialExaminationScoreUseCase createInitialExaminationScoreUseCase;

    @GetMapping("/id")
    public ResponseEntity getId(@RequestParam String section,
                                @RequestParam String category,
                                @RequestParam String display) throws IOException, IOException {
        return ResponseEntity.ok()
                .body(fieldMappingService.lookupByDisplay(section, category, display)
                        .orElse(Collections.singletonList("not Found")));
    }


    @PostMapping(value = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> convertCsvToYaml(@RequestParam("file") MultipartFile file) throws IOException {
        File yamlFile = service.convertCsvToYaml(file);

        byte[] yamlBytes = Files.readAllBytes(yamlFile.toPath());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.yaml")
                .contentType(MediaType.parseMediaType("application/x-yaml"))
                .body(yamlBytes);
    }

    @GetMapping("/omt-test")
    public ResponseEntity readOMTTest() throws IOException, IOException {
        return ResponseEntity.ok()
                .body(readActiveOMTTestUseCase.read());
    }

    @PostMapping("/create/record/note-id/{noteId}")
    public ResponseEntity createScoreRecord(@PathVariable Long noteId) throws IOException {
        createInitialExaminationScoreUseCase.create(noteId);
        return ResponseEntity.ok()
                .build();
    }
}
