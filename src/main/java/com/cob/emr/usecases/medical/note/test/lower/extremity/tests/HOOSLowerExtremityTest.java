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
import java.util.stream.Collectors;

@Component("oos")
public class HOOSLowerExtremityTest implements LowerExtremityTest {
    @Autowired
    OMTTestEntityRepository omtTestEntityRepository;
    private final static String HOOS_TEST = "hoos";
    private final static String KOOS_TEST = "koos";

    @Override
    public Map<String, Double> test(LowerExtremityRequest lowerExtremityRequest) {

        Map<String, Integer> painMap = filterByPrefix(lowerExtremityRequest.getAnswers(), "P");
        Map<String, Integer> symptomsMap = filterByPrefix(lowerExtremityRequest.getAnswers(), "S");
        Map<String, Integer> sportMap = filterByPrefix(lowerExtremityRequest.getAnswers(), "SP");
        Map<String, Integer> adlMap = filterByPrefix(lowerExtremityRequest.getAnswers(), "A");
        Map<String, Integer> qualityOfLifeMap = filterByPrefix(lowerExtremityRequest.getAnswers(), "Q");

        List<OMTTestFormulaEntity> formulas = pickTestFormulas(lowerExtremityRequest.getOosType());

        Map<String, Double> results = new LinkedHashMap<>();
        Double pain = calculateSubScale(getFormulaByName(formulas, "pain"), painMap);
        Double symptoms = calculateSubScale(getFormulaByName(formulas, "symptoms"), symptomsMap);
        Double sport = calculateSubScale(getFormulaByName(formulas, "sport"), sportMap);
        Double adl = calculateSubScale(getFormulaByName(formulas, "adl"), adlMap);
        Double qualityOfLife = calculateSubScale(getFormulaByName(formulas, "qol"), qualityOfLifeMap);
        Double score = calculateScore(getFormulaByName(formulas, "score"), pain, symptoms, adl, sport, qualityOfLife);

        results.put("score", Math.round(score * 100.0) / 100.0);
        return results;
    }

    private List<OMTTestFormulaEntity> pickTestFormulas(String OOSType) {
        switch (OOSType.toLowerCase()) {
            case "hoos":
                OMTTestEntity HOOSTest = omtTestEntityRepository.findByTestName(HOOS_TEST)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid test " + HOOS_TEST));
                return HOOSTest.getFormulas();

            case "koos":
                OMTTestEntity KOOSTest = omtTestEntityRepository.findByTestName(KOOS_TEST)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid test " + KOOS_TEST));
                return KOOSTest.getFormulas();

            default:
                throw new IllegalArgumentException("Invalid OOS type: " + OOSType);
        }

    }

    private double calculateSubScale(OMTTestFormulaEntity formula, Map<String, Integer> answers) {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        Expression exp = parser.parseExpression(formula.getFormula());
        answers.forEach((q, score) -> context.setVariable(q, score.doubleValue()));
        return exp.getValue(context, Double.class);
    }

    private Double calculateScore(OMTTestFormulaEntity formula, Double pain, Double symptoms, Double adl, Double sport, Double qol) {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("PAIN", pain);
        context.setVariable("SYMPTOMS", symptoms);
        context.setVariable("ADL", adl);
        context.setVariable("SPORT", sport);
        context.setVariable("QOL", qol);
        Expression exp = parser.parseExpression(formula.getFormula());
        return exp.getValue(context, Double.class);
    }

    private Map<String, Integer> filterByPrefix(Map<String, Integer> source, String prefix) {
        return source.entrySet()
                .stream()
                .filter(e -> e.getKey().startsWith(prefix))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private OMTTestFormulaEntity getFormulaByName(List<OMTTestFormulaEntity> formulas, String name) {
        return formulas.stream()
                .filter(omtTestFormulaEntity -> omtTestFormulaEntity.getFormulaName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Formula hoos not found"));
    }
}
