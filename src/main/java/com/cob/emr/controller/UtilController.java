package com.cob.emr.controller;

import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Basic;
import com.cob.emr.service.util.GenerateProxyURLs;
import com.cob.emr.usecases.medical.note.UpdateCaseDiagnosisUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/util")
public class UtilController {
    @Autowired
    UpdateCaseDiagnosisUseCase updateCaseDiagnosisUseCase;
    @Autowired
    GenerateProxyURLs generateProxyURLs;
    private final RequestMappingHandlerMapping handlerMapping;
    private Set<String> urls;

    // Make sure we inject the correct one by name
    public UtilController(@Qualifier("requestMappingHandlerMapping")
                          RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @GetMapping("/generate/all-urls")
    public List<String> listAllApis() {
        Set<String> urls = new LinkedHashSet<>();

        handlerMapping.getHandlerMethods().forEach((mappingInfo, handlerMethod) -> {
            // Handle both old (<=2.5) and new (>=2.6) Spring Boot versions
            if (mappingInfo.getPatternsCondition() != null) {
                mappingInfo.getPatternsCondition().getPatterns()
                        .forEach(p -> urls.add("/emr/api" + p));
            }
            if (mappingInfo.getPathPatternsCondition() != null) {
                mappingInfo.getPathPatternsCondition().getPatterns()
                        .forEach(p -> urls.add("/emr/api" + p.getPatternString()));
            }
        });
        this.urls = urls;
        return urls.stream().sorted().collect(Collectors.toList());
    }

    // generate proxy file
    @GetMapping(value = "/generate/proxy-file", produces = "text/plain")
    public String generateProxyFile(@RequestParam String backendURL) {
        if (urls == null || urls.isEmpty()) {
            return "No URLs generated yet. Call /util/generate/all-urls first.";
        }
        return String.join("\n", generateProxyURLs.generateRedirects(urls, backendURL));
    }

    @PutMapping(value = "/convert/{caseId}")
    public String convert(@RequestBody Basic basic, @PathVariable Long caseId) {
        updateCaseDiagnosisUseCase.update(caseId, basic);
        return "done";
    }
}
