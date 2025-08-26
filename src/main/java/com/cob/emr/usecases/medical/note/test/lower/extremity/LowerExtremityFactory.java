package com.cob.emr.usecases.medical.note.test.lower.extremity;

import com.cob.emr.usecases.medical.note.test.lower.extremity.tests.LowerExtremityTest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LowerExtremityFactory {
    private final Map<String, LowerExtremityTest> tests;

    public LowerExtremityFactory(Map<String, LowerExtremityTest> tests) {
        this.tests = tests;
    }

    public LowerExtremityTest getTest(String testName) {
        return tests.get(testName);
    }
}
