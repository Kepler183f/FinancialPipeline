package com.frauddetection.realtime.service;

import com.frauddetection.realtime.dto.FraudAlertDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AlertService {

    private static final Logger logger = LoggerFactory.getLogger(AlertService.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${fraud-detection.topics.fraud-alerts}")
    private String fraudAlertsTopic;

    @Value("${fraud-detection.redis.key-expiration-hours}")
    private int keyExpirationHours;

    public void sendAlert(FraudAlertDto alert) {
        try {
            kafkaTemplate.send(fraudAlertsTopic, alert.getTransactionId(), alert);
            
            storeAlertInRedis(alert);
            
            logger.info("Fraud alert sent for transaction: {} - Severity: {} - Risk Score: {}", 
                    alert.getTransactionId(), alert.getSeverity(), alert.getRiskScore());
                    
        } catch (Exception e) {
            logger.error("Failed to send fraud alert for transaction: {}", alert.getTransactionId(), e);
        }
    }

    private void storeAlertInRedis(FraudAlertDto alert) {
        String key = "alert:" + alert.getAlertId();
        redisTemplate.opsForValue().set(key, alert, keyExpirationHours, TimeUnit.HOURS);
        
        String userAlertsKey = "user-alerts:" + alert.getUserId();
        redisTemplate.opsForList().rightPush(userAlertsKey, alert.getAlertId());
        redisTemplate.expire(userAlertsKey, keyExpirationHours, TimeUnit.HOURS);
    }

    public FraudAlertDto getAlert(String alertId) {
        String key = "alert:" + alertId;
        return (FraudAlertDto) redisTemplate.opsForValue().get(key);
    }
}