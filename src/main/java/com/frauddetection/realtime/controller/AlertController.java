package com.frauddetection.realtime.controller;

import com.frauddetection.realtime.dto.FraudAlertDto;
import com.frauddetection.realtime.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @GetMapping("/{alertId}")
    public ResponseEntity<FraudAlertDto> getAlert(@PathVariable String alertId) {
        FraudAlertDto alert = alertService.getAlert(alertId);
        if (alert != null) {
            return ResponseEntity.ok(alert);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}