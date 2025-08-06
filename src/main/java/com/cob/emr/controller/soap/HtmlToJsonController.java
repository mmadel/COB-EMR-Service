package com.cob.emr.controller.soap;

import com.cob.emr.service.soap.ROMHtmlToJsonService;
import com.cob.emr.service.soap.StrengthHtmlToJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/convert")
public class HtmlToJsonController {
    @Autowired
    private ROMHtmlToJsonService ROMHtmlToJsonService;
    @Autowired
    private StrengthHtmlToJsonService strengthHtmlToJsonService;

    @GetMapping("/rom-html-to-json")
    public List<Map<String, Object>> convertHtmlToJsonROM() {
        return ROMHtmlToJsonService.convertHtml();
    }
    @GetMapping("/strength-html-to-json")
    public List<Map<String, Object>> convertHtmlToJsonStrength(@RequestParam String fieldName) {
        return strengthHtmlToJsonService.convertHtmlToJson(fieldName);
    }

}
