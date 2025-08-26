package com.cob.emr.usecases.medical.note.test.lower.extremity.tests;

import com.cob.emr.entity.medical.note.test.OMTTestEntity;
import com.cob.emr.entity.medical.note.test.OMTTestFormulaEntity;
import com.cob.emr.model.medical.note.omt.test.LowerExtremityRequest;
import com.cob.emr.repositories.medical.note.test.OMTTestEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("faam")
public class FAAMLowerExtremityTest implements LowerExtremityTest {
    @Autowired
    OMTTestEntityRepository omtTestEntityRepository;
    private final static String FAAM_TEST = "faam";
    private final static String HPS_FORMULA = "hps";
    private final static String SUM_FORMULA = "sum";
    private final static String SCORE_FORMULA = "score";
    private final ExpressionParser parser = new SpelExpressionParser();

    @Override
    public Map<String, Double> test(LowerExtremityRequest lowerExtremityRequest) {
        Map<String, Double> results = new LinkedHashMap<>();
        OMTTestEntity test = omtTestEntityRepository.findByTestName(FAAM_TEST)
                .orElseThrow(() -> new IllegalArgumentException("Invalid test " + FAAM_TEST));
        Integer hps = calculateHPS(test.getFormulas(), lowerExtremityRequest.getAnswers().size());
        Integer sum = calculateSum(test.getFormulas(), lowerExtremityRequest.getAnswers());
        Double score = calculateScore(test.getFormulas(), sum, hps);
        results.put("score", Math.round(score * 100.0) / 100.0);
        return results;
    }

    private Integer calculateHPS(List<OMTTestFormulaEntity> formulas, Integer totalAnswers) {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("TA", totalAnswers);
        OMTTestFormulaEntity formula = getFormulaByName(formulas, HPS_FORMULA);
        Expression exp = parser.parseExpression(formula.getFormula());
        return exp.getValue(context, Integer.class);
    }

    private Integer calculateSum(List<OMTTestFormulaEntity> formulas, Map<String, Integer> answers) {
        OMTTestFormulaEntity formula = getFormulaByName(formulas, SUM_FORMULA);

        EvaluationContext context = new StandardEvaluationContext();

        Pattern pattern = Pattern.compile("#(\\w+)");
        Matcher matcher = pattern.matcher(formula.getFormula());

        while (matcher.find()) {
            String var = matcher.group(1);
            // use value from answers or default 0
            context.setVariable(var, answers.getOrDefault(var, 0));
        }

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(formula.getFormula());
        return exp.getValue(context, Integer.class);
    }

    private Double calculateScore(List<OMTTestFormulaEntity> formulas, Integer sum, Integer hps) {
        OMTTestFormulaEntity formula = getFormulaByName(formulas, SCORE_FORMULA);
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("SUM", sum.doubleValue());
        context.setVariable("HPS", hps.doubleValue());
        Expression exp = parser.parseExpression(formula.getFormula());
        return exp.getValue(context, Double.class);
    }

    private OMTTestFormulaEntity getFormulaByName(List<OMTTestFormulaEntity> formulas, String name) {
        return formulas.stream()
                .filter(omtTestFormulaEntity -> omtTestFormulaEntity.getFormulaName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Formula faam not found"));
    }
}
