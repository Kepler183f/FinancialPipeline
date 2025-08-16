package com.frauddetection.realtime.rules.impl;

import com.frauddetection.realtime.model.Transaction;
import com.frauddetection.realtime.rules.FraudRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SuspiciousLocationRule implements FraudRule {

    @Value("${fraud-detection.rules.suspicious-locations}")
    private List<String> suspiciousLocations;

    @Override
    public boolean evaluate(Transaction transaction) {
        return transaction.getLocation() != null && 
               suspiciousLocations.contains(transaction.getLocation());
    }

    @Override
    public String getRuleName() {
        return "SUSPICIOUS_LOCATION_RULE";
    }

    @Override
    public String getDescription() {
        return "Flags transactions from suspicious or high-risk locations";
    }

    @Override
    public int getPriority() {
        return 3;
    }
}