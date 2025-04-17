package com.cob.emr.controller;

import com.cob.emr.service.endpoints.EndpointInspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/endpoints")
public class EndPointController {

    @Autowired
    private EndpointInspector endpointInspector;

    @GetMapping("/get")
    public List<String> listAllEndpoints() {
        return endpointInspector.getEndPoints();
    }
}
