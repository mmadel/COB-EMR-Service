package com.cob.emr.service.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Component
public class EndpointInspector {
    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

    public List<String> getEndPoints() {
        String basePath = "/emr/api";
        String baseUrl = "https://emr-service-d8c19b64ff35.herokuapp.com";

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        List<String> transformed = new ArrayList<>();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();

            Set<String> patterns = mappingInfo.getPatternValues();

            patterns.stream().findFirst().ifPresent(pattern -> {
                String normalizedPath = pattern.replaceAll("\\{([^}]+)\\}", ":$1");
                String fullPath = basePath + normalizedPath;
                String url = baseUrl + fullPath;

                String line = fullPath + " " + url + " 200";
                transformed.add(line);
            });
        }

        return transformed;
    }

}
