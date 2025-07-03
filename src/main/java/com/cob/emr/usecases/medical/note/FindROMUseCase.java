package com.cob.emr.usecases.medical.note;

import com.cob.emr.entity.medical.note.soap.profile.ROMModelEntity;
import com.cob.emr.model.medical.note.soap.profile.rom.ROMModel;
import com.cob.emr.repositories.medical.note.ROMModelEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindROMUseCase {
    @Autowired
    ROMModelEntityRepository romModelEntityRepository;

    public List<ROMModel> find(String romTest) {
        List<ROMModelEntity> ROMTests = romModelEntityRepository.findByRomTestName(romTest)
                .orElseThrow(() -> new IllegalArgumentException("ROM Test not found"));
        return ROMTests.stream()
                .map(romModelEntity -> {
                    ROMModel test = new ROMModel(romModelEntity.getName(), romModelEntity.getValues());
                    return test;
                }).collect(Collectors.toList());

    }
}
