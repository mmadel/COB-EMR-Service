package com.cob.emr.controller;

import com.cob.emr.model.medical.note.types.initial.evaluation.subjective.Subjective;
import com.cob.emr.service.UpdateNoteTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value = "/tmp")
public class TempController {
    @Autowired
    UpdateNoteTest updateNoteTest;
    public static class OrderResponse {
        private String name;
        private int order;

        public OrderResponse(String name, int order) {
            this.name = name;
            this.order = order;
        }

        public String getName() {
            return name;
        }

        public int getOrder() {
            return order;
        }
    }
    @PostMapping("/convert")
    public List<OrderResponse> createOrderList(@RequestBody String body) {
        // Split by comma OR newline
        String[] parts = body.split("[,\\n]");

        List<String> items = Arrays.stream(parts)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        return IntStream.range(0, items.size())
                .mapToObj(i -> new OrderResponse(items.get(i), i + 1))
                .collect(Collectors.toList());
    }
    @PostMapping("/update/note/{id}")
    public ResponseEntity createOrderList(@RequestBody Subjective subjective, @PathVariable Long id) {
        updateNoteTest.dd(id , subjective);
        return ResponseEntity.ok()
                .build();
    }
}
