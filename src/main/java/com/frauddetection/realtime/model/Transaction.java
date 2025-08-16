package com.frauddetection.realtime.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private String userId;
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private String merchantId;
    private String merchantName;
    private String location;
    private String transactionType;
    private LocalDateTime timestamp;
    private String ipAddress;
    private String deviceId;
    private String cardNumber;
    private boolean isOnline;

    public Transaction() {}

    public Transaction(String transactionId, String userId, String accountId, BigDecimal amount,
                      String currency, String merchantId, String merchantName, String location,
                      String transactionType, LocalDateTime timestamp, String ipAddress,
                      String deviceId, String cardNumber, boolean isOnline) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.accountId = accountId;
        this.amount = amount;
        this.currency = currency;
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.location = location;
        this.transactionType = transactionType;
        this.timestamp = timestamp;
        this.ipAddress = ipAddress;
        this.deviceId = deviceId;
        this.cardNumber = cardNumber;
        this.isOnline = isOnline;
    }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }

    public String getMerchantName() { return merchantName; }
    public void setMerchantName(String merchantName) { this.merchantName = merchantName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public boolean isOnline() { return isOnline; }
    public void setOnline(boolean online) { isOnline = online; }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", userId='" + userId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", location='" + location + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", timestamp=" + timestamp +
                ", isOnline=" + isOnline +
                '}';
    }
}