package com.cob.emr.service.soap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@Service
public class StrengthHtmlToJsonService {
    @Value("${html.input.file.strength:/home/mohamed/html/input.html}")
    private String htmlInputFile;

    public List<Map<String, Object>> convertHtmlToJson(String fieldName) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            Path path = Paths.get(htmlInputFile);
            try (InputStream htmlStream = Files.newInputStream(path)) {
                Document doc = Jsoup.parse(htmlStream, "UTF-8", "");

                Elements rows = doc.select("div.rowdiv");

                List<Map<String, Object>> topControls = new ArrayList<>();
                List<Map<String, Object>> controlR = new ArrayList<>();
                List<Map<String, Object>> controlL = new ArrayList<>();

                for (Element row : rows) {
                    String label = Optional.ofNullable(row.selectFirst("div.col1div"))
                            .map(Element::text)
                            .orElse("").trim();

                    // Case 1: Top controls (single select in .col2div)
                    Element col2Div = row.selectFirst("div.col2div");
                    if (col2Div != null) {
                        Element select = col2Div.selectFirst("select");
                        if (select != null) {
                            topControls.add(buildControl(select, label));
                            continue; // skip further processing of this row
                        }
                    }

                    // Case 2: Dual controls (right and left)
                    Element rightDiv = row.selectFirst("div.colRightdiv");
                    if (rightDiv != null) {
                        Element select = rightDiv.selectFirst("select");
                        if (select != null) {
                            controlR.add(buildControl(select, label));
                        }
                    }

                    Element leftDiv = row.selectFirst("div.colLeftdiv");
                    if (leftDiv != null) {
                        Element select = leftDiv.selectFirst("select");
                        if (select != null) {
                            controlL.add(buildControl(select, label));
                        }
                    }
                }

                // Wrap up JSON structure
                Map<String, Object> jsonObject = new LinkedHashMap<>();
                jsonObject.put("fieldName", fieldName);
                jsonObject.put("fieldType", "top-right-left-selects");
                jsonObject.put("ignoreDisplay", true);

                Map<String, Object> valueMap = new LinkedHashMap<>();
                valueMap.put("topControls", topControls);
                valueMap.put("controlR", controlR);
                valueMap.put("controlL", controlL);

                jsonObject.put("value", valueMap);
                resultList.add(jsonObject);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse HTML", e);
        }

        return resultList;
    }



    private Map<String, Object> buildControl(Element select, String label) {
        Map<String, Object> control = new LinkedHashMap<>();
        control.put("label", label);

        List<Map<String, String>> valueList = new ArrayList<>();
        Elements options = select.select("option");

        for (Element option : options) {
            if (option.hasAttr("value")) {
                Map<String, String> valObj = new LinkedHashMap<>();
                valObj.put("val", option.attr("value"));
                valObj.put("view", option.text().trim());
                valueList.add(valObj);
            }
        }

        control.put("value", valueList);
        return control;
    }
}
