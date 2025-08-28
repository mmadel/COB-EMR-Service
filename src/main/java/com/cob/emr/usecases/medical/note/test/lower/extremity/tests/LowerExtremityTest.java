package com.cob.emr.usecases.medical.note.test.lower.extremity.tests;

import com.cob.emr.model.medical.note.omt.test.LowerExtremityRequest;

import java.util.Map;

public interface LowerExtremityTest {
    Map<String, Double> test(LowerExtremityRequest lowerExtremityRequest);
}
