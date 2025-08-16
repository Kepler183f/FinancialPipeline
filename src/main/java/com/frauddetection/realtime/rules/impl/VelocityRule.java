package com.frauddetection.realtime.rules.impl;

import com.frauddetection.realtime.model.Transaction;
import com.frauddetection.realtime.rules.FraudRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
public class VelocityRule implements FraudRule {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${fraud-detection.rules.velocity-check-window-minutes}")
    private int windowMinutes;

    @Value("${fraud-detection.rules.max-transactions-per-window}")
    private int maxTransactionsPerWindow;

    @Override
    public boolean evaluate(Transaction transaction) {
        String key = "velocity:" + transaction.getUserId();
        String currentMinute = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
        
        String windowKey = key + ":" + currentMinute;
        
        Long count = redisTemplate.opsForValue().increment(windowKey);
        redisTemplate.expire(windowKey, windowMinutes, TimeUnit.MINUTES);
        
        return count != null && count > maxTransactionsPerWindow;
    }

    @Override
    public String getRuleName() {
        return "VELOCITY_RULE";
    }

    @Override
    public String getDescription() {
        return "Flags users with too many transactions in a short time window";
    }

    @Override
    public int getPriority() {
        return 2;
    }
}