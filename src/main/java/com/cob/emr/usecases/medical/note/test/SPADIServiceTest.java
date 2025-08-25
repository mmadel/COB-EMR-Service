package com.cob.emr.usecases.medical.note.test;

import com.cob.emr.entity.medical.note.test.OMTTestEntity;
import com.cob.emr.entity.medical.note.test.OMTTestFormulaEntity;
import com.cob.emr.model.medical.note.omt.test.SPADIRequest;
import com.cob.emr.repositories.medical.note.test.OMTTestEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SPADIServiceTest {
    @Autowired
    OMTTestEntityRepository omtTestEntityRepository;
    private final static String SPADI_TEST = "spadi";
    private final static String PAIN_FORMULA = "pain";
    private final static String DISABILITY_FORMULA = "disability";

    public Map<String, Double> test(SPADIRequest spadiRequest) {
        Map<String, Double> results = new LinkedHashMap<>();
        OMTTestEntity test = omtTestEntityRepository.findByTestName(SPADI_TEST)
                .orElseThrow(() -> new IllegalArgumentException("Invalid test " + SPADI_TEST));
        Double pain = calculatePain(spadiRequest.getPain(),test.getFormulas());
        Double disability = calculateDisability(spadiRequest.getDisability(),test.getFormulas());
        Double total =(pain +disability)/2L;
        results.put("pain",Math.round(pain * 100.0) / 100.0);
        results.put("disability",Math.round(disability * 100.0) / 100.0);
        results.put("total",Math.round(total * 100.0) / 100.0);
        return results;
    }

    private Double calculatePain(Map<String, Integer> pain, List<OMTTestFormulaEntity> formulas) {
        ExpressionParser parser = new SpelExpressionParser();
        OMTTestFormulaEntity formula = getFormulaByName(formulas, PAIN_FORMULA);
        EvaluationContext context = new StandardEvaluationContext();
        pain.forEach((q, score) -> context.setVariable(q, score.doubleValue()));
        Expression exp = parser.parseExpression(formula.getFormula());
        return exp.getValue(context, Double.class);
    }

    private Double calculateDisability(Map<String, Integer> disability, List<OMTTestFormulaEntity> formulas) {
        ExpressionParser parser = new SpelExpressionParser();
        OMTTestFormulaEntity formula = getFormulaByName(formulas, DISABILITY_FORMULA);
        EvaluationContext context = new StandardEvaluationContext();
        disability.forEach((q, score) -> context.setVariable(q, score.doubleValue()));
        Expression exp = parser.parseExpression(formula.getFormula());
        return exp.getValue(context, Double.class);
    }

    private OMTTestFormulaEntity getFormulaByName(List<OMTTestFormulaEntity> formulas, String name) {
        return formulas.stream()
                .filter(omtTestFormulaEntity -> omtTestFormulaEntity.getFormulaName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Formula SPADI not found"));
    }
}

