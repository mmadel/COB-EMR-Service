package com.cob.emr.service.util;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GenerateProxyURLs {
    private static final String PRODUCTION_BASE_URL = "https://patient-intake-a7d7a58ddf0b.herokuapp.com";
    private static final String STAGE_BASE_URL = "http://161.97.126.192:8070";
    private static final String STATUS_CODE = " 200"; // keep consistent formatting
    /**
     * Generates redirect rules based on urls set
     */
    public List<String> generateRedirects(Set<String> urls,String backendURL) {
        List<String> redirects = new ArrayList<>();

        for (String url : urls) {
            String formattedLine = url.replaceAll("\\{(\\w+)}", ":$1");
            String redirectLine = formattedLine + " " + backendURL + formattedLine + STATUS_CODE;
            redirects.add(redirectLine);
        }

        return redirects;
    }
}
