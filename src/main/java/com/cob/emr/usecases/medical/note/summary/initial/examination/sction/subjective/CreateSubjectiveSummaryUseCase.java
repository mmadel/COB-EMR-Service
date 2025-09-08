package com.cob.emr.usecases.medical.note.summary.initial.examination.sction.subjective;

import com.cob.emr.model.medical.note.summary.soap.SubjectiveSummary;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.*;
import com.cob.emr.service.mapper.FieldMappingService;
import com.cob.emr.usecases.medical.note.summary.FieldProcessorService;
import com.cob.emr.usecases.medical.note.summary.reader.SubjectiveReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CreateSubjectiveSummaryUseCase {
    private final String SECTION = "subjective";
    private final String BASIC_CATEGORY = "basicInformation";
    private final String PAIN_CATEGORY = "pain";
    private final String PRIOR_FUNCTION_CATEGORY = "prior_level_of_function";
    private final String CURRENT_FUNCTION_CATEGORY = "current_functional_limitations";
    private final String FUNCTION_FIELD_FILE = "prior.function" + ".yaml";

    @Autowired
    SubjectiveReaderService subjectiveReaderService;
    @Autowired
    FieldMappingService fieldMappingService;
    @Autowired
    FieldProcessorService fieldProcessorService;

    public SubjectiveSummary create(Subjective subjective) throws IOException {
        SubjectiveSummary subjectiveSummary = new SubjectiveSummary();
        subjectiveSummary.setBasicInformation(constructBasicInformation(subjective.getBasic()));
        subjectiveSummary.setPain(constructPain(subjective.getPain()));
        subjectiveSummary.setPriorFunction(constructPriorLevelOfFunction(subjective.getPriorFunction()));
        subjectiveSummary.setCurrentFunction(constructCurrentFunction(subjective.getCurrentFunction()));
        return subjectiveSummary;
    }

    private Map<String, Object> constructBasicInformation(Basic basic) throws IOException {
        String file = "basic.information" + ".yaml";
        Map<String, Object> displayBasicInformation = subjectiveReaderService.loadYaml(file);

        Map.Entry<String, Object> entry = displayBasicInformation.entrySet().iterator().next();
        String key = entry.getKey();
        Map<String, Object> fields = (Map<String, Object>) entry.getValue();
        return fieldProcessorService.processFields(fields, basic, SECTION, BASIC_CATEGORY);
    }

    private Map<String, Object> constructPain(Pain pain) throws IOException {
        String file = "pain" + ".yaml";
        Map<String, Object> painData = subjectiveReaderService.loadYaml(file);

        Map.Entry<String, Object> entry = painData.entrySet().iterator().next();
        String key = entry.getKey();
        Map<String, Object> fields = (Map<String, Object>) entry.getValue();
        Map<String, Object> processedFields =fieldProcessorService.processFields(fields, pain, SECTION, PAIN_CATEGORY);
        processedFields.put("Pain Scale", pain.getEvals());
        processedFields.put("Pain Comment" , pain.getRestrictionsAndPainAlleviatorsTextArea());
        return processedFields;
    }

    private Map<String, Object> constructPriorLevelOfFunction(PriorFunction priorFunction) throws IOException {
        Map<String, Object> priorFunctionData = subjectiveReaderService.loadYaml(FUNCTION_FIELD_FILE);
        Map.Entry<String, Object> entry = priorFunctionData.entrySet().iterator().next();
        String key = entry.getKey();
        Map<String, Object> fields = (Map<String, Object>) entry.getValue();
        return fieldProcessorService.processFields(fields, priorFunction, SECTION, PRIOR_FUNCTION_CATEGORY);
    }
    private Map<String, Object> constructCurrentFunction(CurrentFunction currentFunction) throws IOException {

        Map<String, Object> currentFunctionData = subjectiveReaderService.loadYaml(FUNCTION_FIELD_FILE);
        Map.Entry<String, Object> entry = currentFunctionData.entrySet().iterator().next();
        String key = entry.getKey();
        Map<String, Object> fields = (Map<String, Object>) entry.getValue();
        return fieldProcessorService.processFields(fields, currentFunction, SECTION, CURRENT_FUNCTION_CATEGORY);
    }

}
