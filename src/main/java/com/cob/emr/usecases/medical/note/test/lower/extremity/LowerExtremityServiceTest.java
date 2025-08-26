package com.cob.emr.usecases.medical.note.test.lower.extremity;

import com.cob.emr.model.medical.note.omt.test.LowerExtremityRequest;
import com.cob.emr.usecases.medical.note.test.lower.extremity.tests.LowerExtremityTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LowerExtremityServiceTest {
    @Autowired
    LowerExtremityFactory factory;

    public Map<String, Double> execute(String testName, LowerExtremityRequest request) {
        LowerExtremityTest lowerExtremityTest = factory.getTest(testName);
        if (lowerExtremityTest == null)
            throw new IllegalArgumentException("Test not found");
        return lowerExtremityTest.test(request);

    }
}
