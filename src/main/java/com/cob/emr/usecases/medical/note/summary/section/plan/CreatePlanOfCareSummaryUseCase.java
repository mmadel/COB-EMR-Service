package com.cob.emr.usecases.medical.note.summary.section.plan;

import com.cob.emr.entity.medical.note.types.InitialEvaluation;
import com.cob.emr.model.medical.note.finalize.init.exam.plan.InitPlanOfCare;
import com.cob.emr.model.medical.note.summary.soap.PlanOfCareSummary;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.header.DurationMapper;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.header.FrequencyMapper;
import com.cob.emr.usecases.medical.note.post.init.note.plan.mappers.header.PlanMapper;
import com.cob.emr.usecases.medical.note.post.init.note.plan.reader.ModalitiesReader;
import com.cob.emr.usecases.medical.note.post.init.note.plan.reader.ProceduresReader;
import com.cob.emr.usecases.medical.note.post.init.note.plan.reader.SpecialtiesReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class CreatePlanOfCareSummaryUseCase {
    @Autowired
    private ProceduresReader proceduresReader;
    @Autowired
    private ModalitiesReader modalitiesReader;
    @Autowired
    private SpecialtiesReader specialtiesReader;
    public PlanOfCareSummary create(InitialEvaluation initialEvaluation) throws IOException {
        PlanOfCareSummary planOfCareSummary = new PlanOfCareSummary();

        if (initialEvaluation.getPlanOfCare().getProcedures() != null) {
            Map<String, List<String>> procedures = proceduresReader.read(initialEvaluation.getPlanOfCare().getProcedures());
            planOfCareSummary.setProcedures(procedures);
        }

        if (initialEvaluation.getPlanOfCare().getModalities() != null) {
            Map<String, List<String>> modalities = modalitiesReader.read(initialEvaluation.getPlanOfCare().getModalities());
            planOfCareSummary.setModalities(modalities);
        }

        if (initialEvaluation.getPlanOfCare().getSpecialties() != null) {
            Map<String, List<String>> specialties = specialtiesReader.read(initialEvaluation.getPlanOfCare().getSpecialties());
            planOfCareSummary.setSpecialties(specialties);
        }
        if (initialEvaluation.getPlanOfCare().getFrequency() != null)
            planOfCareSummary.setFrequency(FrequencyMapper.getFrequencyLabel(initialEvaluation.getPlanOfCare().getFrequency()).get());
        if (initialEvaluation.getPlanOfCare().getDuration() != null)
            planOfCareSummary.setDuration(DurationMapper.getDurationLabel(initialEvaluation.getPlanOfCare().getDuration()).get());
        if (initialEvaluation.getPlanOfCare().getPlan() != null)
            planOfCareSummary.setPlan(PlanMapper.getPlanLabel(initialEvaluation.getPlanOfCare().getPlan()).get());
        return planOfCareSummary;
    }
}
