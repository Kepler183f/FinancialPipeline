package com.frauddetection.realtime.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class FraudAlertDto {
    private String alertId;
    private String transactionId;
    private String userId;
    private String accountId;
    private BigDecimal amount;
    private String riskScore;
    private String alertType;
    private List<String> triggeredRules;
    private String severity;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime detectedAt;
    
    private String status;
    private String description;

    public FraudAlertDto() {}

    public FraudAlertDto(String alertId, String transactionId, String userId, String accountId,
                        BigDecimal amount, String riskScore, String alertType, List<String> triggeredRules,
                        String severity, LocalDateTime detectedAt, String status, String description) {
        this.alertId = alertId;
        this.transactionId = transactionId;
        this.userId = userId;
        this.accountId = accountId;
        this.amount = amount;
        this.riskScore = riskScore;
        this.alertType = alertType;
        this.triggeredRules = triggeredRules;
        this.severity = severity;
        this.detectedAt = detectedAt;
        this.status = status;
        this.description = description;
    }

    public String getAlertId() { return alertId; }
    public void setAlertId(String alertId) { this.alertId = alertId; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getRiskScore() { return riskScore; }
    public void setRiskScore(String riskScore) { this.riskScore = riskScore; }

    public String getAlertType() { return alertType; }
    public void setAlertType(String alertType) { this.alertType = alertType; }

    public List<String> getTriggeredRules() { return triggeredRules; }
    public void setTriggeredRules(List<String> triggeredRules) { this.triggeredRules = triggeredRules; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public LocalDateTime getDetectedAt() { return detectedAt; }
    public void setDetectedAt(LocalDateTime detectedAt) { this.detectedAt = detectedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}