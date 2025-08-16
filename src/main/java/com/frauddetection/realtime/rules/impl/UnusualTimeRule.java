package com.frauddetection.realtime.rules.impl;

import com.frauddetection.realtime.model.Transaction;
import com.frauddetection.realtime.rules.FraudRule;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class UnusualTimeRule implements FraudRule {

    private static final LocalTime NIGHT_START = LocalTime.of(23, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    @Override
    public boolean evaluate(Transaction transaction) {
        if (transaction.getTimestamp() == null) {
            return false;
        }
        
        LocalTime transactionTime = transaction.getTimestamp().toLocalTime();
        
        return transactionTime.isAfter(NIGHT_START) || transactionTime.isBefore(NIGHT_END);
    }

    @Override
    public String getRuleName() {
        return "UNUSUAL_TIME_RULE";
    }

    @Override
    public String getDescription() {
        return "Flags transactions that occur during unusual hours (11 PM - 6 AM)";
    }

    @Override
    public int getPriority() {
        return 4;
    }
}