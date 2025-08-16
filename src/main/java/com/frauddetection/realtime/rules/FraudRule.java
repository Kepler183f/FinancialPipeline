package com.frauddetection.realtime.rules;

import com.frauddetection.realtime.model.Transaction;

public interface FraudRule {
    boolean evaluate(Transaction transaction);
    String getRuleName();
    String getDescription();
    int getPriority();
}