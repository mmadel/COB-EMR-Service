package com.cob.emr.usecases.medical.note.test;

import com.cob.emr.entity.medical.note.test.OMTTestEntity;
import com.cob.emr.entity.medical.note.test.OMTTestFormulaEntity;
import com.cob.emr.repositories.medical.note.test.OMTTestEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
@Service
public class DASHServiceTest {
    @Autowired
    OMTTestEntityRepository omtTestEntityRepository;
    private final static String DASH_TEST ="dash";
    private final ExpressionParser parser = new SpelExpressionParser();

    public Map<String, Double> test(Map<String, Integer> answers) {
        OMTTestEntity test= omtTestEntityRepository.findByTestName(DASH_TEST)
                .orElseThrow(() -> new IllegalArgumentException("Invalid test " + DASH_TEST));

        EvaluationContext context = new StandardEvaluationContext();
        answers.forEach((q, score) -> context.setVariable(q, score));

        Map<String, Double> results = new LinkedHashMap<>();
        for (OMTTestFormulaEntity formula : test.getFormulas()) {
            Expression exp = parser.parseExpression(formula.getFormula());
            Double value = exp.getValue(context, Double.class);
            results.put(formula.getFormulaName(),  Math.round(value * 100.0) / 100.0);
        }
        return results;
    }
}
