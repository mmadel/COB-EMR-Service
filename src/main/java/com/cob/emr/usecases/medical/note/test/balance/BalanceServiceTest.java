package com.cob.emr.usecases.medical.note.test.balance;

import com.cob.emr.entity.medical.note.test.OMTTestEntity;
import com.cob.emr.entity.medical.note.test.OMTTestFormulaEntity;
import com.cob.emr.model.medical.note.omt.test.BalanceRequest;
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
public class BalanceServiceTest {
    private final static String BERG_TEST = "berg";
    private final static String ABC_TEST = "abc";
    private final static String TINETTI_TEST = "tinetti";
    private final static String FAB_TEST = "fab";

    private final ExpressionParser parser = new SpelExpressionParser();
    @Autowired
    OMTTestEntityRepository omtTestEntityRepository;

    public Map<String, Double> test(BalanceRequest balanceRequest) {
        OMTTestEntity pickTestType = pickTestType(balanceRequest.getTestType());
        OMTTestFormulaEntity formula = pickFormulaTest(pickTestType.getTestName());

        EvaluationContext context = new StandardEvaluationContext();
        balanceRequest.getAnswers().forEach((q, score) -> context.setVariable(q, score.doubleValue()));

        Map<String, Double> results = new LinkedHashMap<>();
        Expression exp = parser.parseExpression(formula.getFormula());
        Double value = exp.getValue(context, Double.class);
        results.put(formula.getFormulaName(), Math.round(value * 100.0) / 100.0);
        return results;
    }

    private OMTTestEntity pickTestType(String testType) {
        switch (testType) {
            case BERG_TEST:
                return omtTestEntityRepository.findByTestName(BERG_TEST)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid test " + BERG_TEST));
            case ABC_TEST:
                return omtTestEntityRepository.findByTestName(ABC_TEST)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid test " + ABC_TEST));
            case TINETTI_TEST:
                return omtTestEntityRepository.findByTestName(TINETTI_TEST)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid test " + TINETTI_TEST));
            case FAB_TEST:
                return omtTestEntityRepository.findByTestName(FAB_TEST)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid test " + FAB_TEST));
            default:
                throw new IllegalArgumentException("Invalid test type: " + testType);
        }
    }

    private OMTTestFormulaEntity pickFormulaTest(String testName) {
        OMTTestEntity test = omtTestEntityRepository.findByTestName(testName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid test " + testName));
        return test.getFormulas().stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Formula for  " + testName + " not found"));
    }
}
