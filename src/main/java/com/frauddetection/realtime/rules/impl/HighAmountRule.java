package com.frauddetection.realtime.rules.impl;

import com.frauddetection.realtime.model.Transaction;
import com.frauddetection.realtime.rules.FraudRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class HighAmountRule implements FraudRule {
    
    @Value("${fraud-detection.rules.max-amount-threshold}")
    private BigDecimal maxAmountThreshold;

    @Override
    public boolean evaluate(Transaction transaction) {
        return transaction.getAmount().compareTo(maxAmountThreshold) > 0;
    }

    @Override
    public String getRuleName() {
        return "HIGH_AMOUNT_RULE";
    }

    @Override
    public String getDescription() {
        return "Flags transactions above the maximum amount threshold";
    }

    @Override
    public int getPriority() {
        return 1;
    }
}