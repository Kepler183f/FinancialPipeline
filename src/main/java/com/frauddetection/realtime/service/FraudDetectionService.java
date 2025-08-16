package com.frauddetection.realtime.service;

import com.frauddetection.realtime.dto.FraudAlertDto;
import com.frauddetection.realtime.dto.TransactionDto;
import com.frauddetection.realtime.model.Transaction;
import com.frauddetection.realtime.rules.FraudRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FraudDetectionService {

    private static final Logger logger = LoggerFactory.getLogger(FraudDetectionService.class);

    @Autowired
    private List<FraudRule> fraudRules;

    @Autowired
    private AlertService alertService;

    @Autowired
    private TransactionMappingService mappingService;

    public void processTransaction(TransactionDto transactionDto) {
        logger.debug("Processing transaction: {}", transactionDto.getTransactionId());
        
        Transaction transaction = mappingService.toEntity(transactionDto);
        
        List<String> triggeredRules = fraudRules.stream()
                .filter(rule -> rule.evaluate(transaction))
                .sorted((r1, r2) -> Integer.compare(r1.getPriority(), r2.getPriority()))
                .map(FraudRule::getRuleName)
                .collect(Collectors.toList());

        if (!triggeredRules.isEmpty()) {
            FraudAlertDto alert = createFraudAlert(transaction, triggeredRules);
            alertService.sendAlert(alert);
            logger.warn("Fraud detected for transaction: {} - Rules: {}", 
                    transaction.getTransactionId(), triggeredRules);
        } else {
            logger.debug("Transaction {} passed all fraud checks", transaction.getTransactionId());
        }
    }

    private FraudAlertDto createFraudAlert(Transaction transaction, List<String> triggeredRules) {
        String alertId = UUID.randomUUID().toString();
        String riskScore = calculateRiskScore(triggeredRules);
        String severity = determineSeverity(triggeredRules.size());
        String description = "Fraud detected - Rules triggered: " + String.join(", ", triggeredRules);

        return new FraudAlertDto(
                alertId,
                transaction.getTransactionId(),
                transaction.getUserId(),
                transaction.getAccountId(),
                transaction.getAmount(),
                riskScore,
                "TRANSACTION_FRAUD",
                triggeredRules,
                severity,
                LocalDateTime.now(),
                "ACTIVE",
                description
        );
    }

    private String calculateRiskScore(List<String> triggeredRules) {
        int score = triggeredRules.size() * 25;
        return Math.min(score, 100) + "%";
    }

    private String determineSeverity(int ruleCount) {
        if (ruleCount >= 3) return "CRITICAL";
        if (ruleCount >= 2) return "HIGH";
        return "MEDIUM";
    }
}