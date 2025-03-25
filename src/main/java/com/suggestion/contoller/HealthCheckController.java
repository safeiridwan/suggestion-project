package com.suggestion.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @GetMapping()
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> out = new HashMap<>();

        out.put("service", "service-suggestion-test");
        out.put("Time", new Date().toString());

        return new ResponseEntity<>(out, HttpStatus.OK);
    }

}
