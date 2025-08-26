package com.cob.emr.usecases.medical.note.test.lower.extremity.tests;

import com.cob.emr.model.medical.note.omt.test.LowerExtremityRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("koos")
public class KOOSLowerExtremityTest implements LowerExtremityTest {
    @Override
    public Map<String, Double> test(LowerExtremityRequest lowerExtremityRequest) {
        return null;
    }
}
