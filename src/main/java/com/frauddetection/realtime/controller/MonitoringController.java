package com.frauddetection.realtime.controller;

import com.frauddetection.realtime.rules.FraudRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/monitoring")
public class MonitoringController {

    @Autowired
    private List<FraudRule> fraudRules;

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("service", "Fraud Detection System");
        return ResponseEntity.ok(status);
    }

    @GetMapping("/rules")
    public ResponseEntity<List<Map<String, Object>>> getFraudRules() {
        List<Map<String, Object>> rulesInfo = fraudRules.stream()
                .map(rule -> {
                    Map<String, Object> ruleInfo = new HashMap<>();
                    ruleInfo.put("name", rule.getRuleName());
                    ruleInfo.put("description", rule.getDescription());
                    ruleInfo.put("priority", rule.getPriority());
                    return ruleInfo;
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(rulesInfo);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getSystemStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRules", fraudRules.size());
        stats.put("systemStatus", "ACTIVE");
        stats.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(stats);
    }
}