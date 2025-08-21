package com.cob.emr.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping(value = "/tmp")
public class TempController {
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
}
