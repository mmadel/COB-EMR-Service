package com.cob.emr.service.soap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.*;
import java.util.*;

@Service
public class HtmlToJsonService {

    // External file path (can be injected via application.properties)
    @Value("${html.input.file:/home/mohamed/html/input.html}")
    private String htmlInputFile;

    public List<Map<String, Object>> convertHtml() {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            Path path = Paths.get(htmlInputFile);
            try (InputStream htmlStream = Files.newInputStream(path)) {
                Document doc = Jsoup.parse(htmlStream, "UTF-8", "");
                Elements rows = doc.select("div.rowdiv");

                // Collect all controlR and controlL elements
                List<Map<String, Object>> controlRList = new ArrayList<>();
                List<Map<String, Object>> controlLList = new ArrayList<>();

                for (Element row : rows) {
                    String label = row.selectFirst("div.col1div").text();

                    // Right Column
                    Element rightDiv = row.selectFirst("div.colRightdiv");
                    if (rightDiv != null) {
                        controlRList.add(buildControl(rightDiv, label));
                    }

                    // Left Column
                    Element leftDiv = row.selectFirst("div.colLeftdiv");
                    if (leftDiv != null) {
                        controlLList.add(buildControl(leftDiv, label));
                    }
                }

                // Construct final JSON object
                Map<String, Object> jsonObject = new LinkedHashMap<>();
                jsonObject.put("fieldName", "shoulder_AROM_rom-test-right-left");
                jsonObject.put("fieldType", "rom-test-right-left");
                jsonObject.put("ignoreDisplay", true);

                Map<String, Object> valueMap = new LinkedHashMap<>();
                valueMap.put("columns", 2);
                valueMap.put("controlR", controlRList);
                valueMap.put("controlL", controlLList);

                jsonObject.put("value", valueMap);
                resultList.add(jsonObject);

            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse HTML", e);
        }

        return resultList;
    }

    private Map<String, Object> buildControl(Element colDiv, String label) {
        Map<String, Object> control = new LinkedHashMap<>();
        control.put("label", label);

        List<Map<String, String>> valueList = new ArrayList<>();
        Elements options = colDiv.select("select option");

        for (Element option : options) {
            if (option.hasAttr("value")) {
                Map<String, String> valObj = new LinkedHashMap<>();
                valObj.put("val", option.attr("value"));
                valObj.put("view", option.text());
                valueList.add(valObj);
            }
        }

        control.put("value", valueList);
        return control;
    }
}
