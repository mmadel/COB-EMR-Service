package com.cob.emr.controller.soap;

import com.cob.emr.service.soap.HtmlToJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/convert")
public class HtmlToJsonController {
    @Autowired
    private HtmlToJsonService htmlToJsonService;

    @GetMapping("/html-to-json")
    public List<Map<String, Object>> convertHtmlToJson() {
        return htmlToJsonService.convertHtml();
    }
}
