package com.frauddetection.realtime.consumer;

import com.frauddetection.realtime.dto.FraudAlertDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class FraudAlertConsumer {

    private static final Logger logger = LoggerFactory.getLogger(FraudAlertConsumer.class);

    @KafkaListener(
            topics = "${fraud-detection.topics.fraud-alerts}",
            groupId = "fraud-alert-notification-group"
    )
    public void consumeFraudAlert(@Payload FraudAlertDto alert) {
        logger.info("Received fraud alert - AlertId: {}, TransactionId: {}, Severity: {}, RiskScore: {}",
                alert.getAlertId(), alert.getTransactionId(), alert.getSeverity(), alert.getRiskScore());
        
        processAlert(alert);
    }

    private void processAlert(FraudAlertDto alert) {
        switch (alert.getSeverity()) {
            case "CRITICAL":
                sendImmediateNotification(alert);
                blockTransaction(alert);
                break;
            case "HIGH":
                sendUrgentNotification(alert);
                flagTransaction(alert);
                break;
            case "MEDIUM":
                sendStandardNotification(alert);
                break;
            default:
                logger.debug("Processing alert with unknown severity: {}", alert.getSeverity());
        }
    }

    private void sendImmediateNotification(FraudAlertDto alert) {
        logger.warn("CRITICAL FRAUD ALERT - Immediate action required for transaction: {} - User: {}",
                alert.getTransactionId(), alert.getUserId());
    }

    private void sendUrgentNotification(FraudAlertDto alert) {
        logger.warn("HIGH RISK FRAUD ALERT - Review required for transaction: {} - User: {}",
                alert.getTransactionId(), alert.getUserId());
    }

    private void sendStandardNotification(FraudAlertDto alert) {
        logger.info("FRAUD ALERT - Monitor transaction: {} - User: {}",
                alert.getTransactionId(), alert.getUserId());
    }

    private void blockTransaction(FraudAlertDto alert) {
        logger.warn("Transaction {} has been BLOCKED due to critical fraud detection", alert.getTransactionId());
    }

    private void flagTransaction(FraudAlertDto alert) {
        logger.info("Transaction {} has been FLAGGED for manual review", alert.getTransactionId());
    }
}