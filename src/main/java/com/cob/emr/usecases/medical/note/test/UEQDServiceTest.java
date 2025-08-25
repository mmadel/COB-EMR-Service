package com.cob.emr.usecases.medical.note.test;

import com.cob.emr.repositories.medical.note.test.OMTTestEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

// Upper extremity quick dash score
@Service
public class UEQDServiceTest {

    @Autowired
    OMTTestEntityRepository omtTestEntityRepository;

    public Integer test(Map<String, Integer> answers) {
        return 0;
    }
}
