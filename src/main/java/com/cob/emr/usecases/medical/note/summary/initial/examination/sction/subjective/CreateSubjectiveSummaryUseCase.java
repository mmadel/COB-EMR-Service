package com.cob.emr.usecases.medical.note.summary.initial.examination.sction.subjective;

import com.cob.emr.model.medical.note.summary.soap.SubjectiveSummary;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Basic;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Pain;
import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Subjective;
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
        Map<String, Object> displayBasicInformation = subjectiveReaderService.loadYaml(file);

        Map.Entry<String, Object> entry = displayBasicInformation.entrySet().iterator().next();
        String key = entry.getKey();
        Map<String, Object> fields = (Map<String, Object>) entry.getValue();
        Map<String, Object> processedFields =fieldProcessorService.processFields(fields, pain, SECTION, PAIN_CATEGORY);
        processedFields.put("Pain Scale", pain.getEvals());
        processedFields.put("Pain Comment" , pain.getRestrictionsAndPainAlleviatorsTextArea());
        return processedFields;
    }

}
