package com.cob.emr.usecases.medical.note.updater.objective;

import com.cob.emr.model.medical.note.types.initial.evaluation.billing.Billing;
import com.cob.emr.model.medical.note.types.initial.evaluation.billing.CPTModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ResetEvaluationReEvaluationCodeUseCase {
    private static final ObjectMapper mapper = new ObjectMapper();
    public Billing resetUntimedCodes(Billing obj) {
        if (obj == null) {
            return null;
        }
        List<CPTModel> untimedCodes = obj.getUntimedCodes();
        Set<String> keysToTransform = Set.of("97161", "97162", "97163", "97164");

        for (int i = 0; i < untimedCodes.size(); i++) {
            CPTModel code = untimedCodes.get(i);
            if (code != null && keysToTransform.contains(code.getCpt())) {
                untimedCodes.set(i, null);  // ✅ Replace the object with null
            }
        }
        obj.setUntimedCodes(untimedCodes);
        return obj;
    }
}
