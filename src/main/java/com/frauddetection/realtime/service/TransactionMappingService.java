package com.frauddetection.realtime.service;

import com.frauddetection.realtime.dto.TransactionDto;
import com.frauddetection.realtime.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionMappingService {

    public Transaction toEntity(TransactionDto dto) {
        return new Transaction(
                dto.getTransactionId(),
                dto.getUserId(),
                dto.getAccountId(),
                dto.getAmount(),
                dto.getCurrency(),
                dto.getMerchantId(),
                dto.getMerchantName(),
                dto.getLocation(),
                dto.getTransactionType(),
                dto.getTimestamp(),
                dto.getIpAddress(),
                dto.getDeviceId(),
                dto.getCardNumber(),
                dto.isOnline()
        );
    }

    public TransactionDto toDto(Transaction entity) {
        TransactionDto dto = new TransactionDto();
        dto.setTransactionId(entity.getTransactionId());
        dto.setUserId(entity.getUserId());
        dto.setAccountId(entity.getAccountId());
        dto.setAmount(entity.getAmount());
        dto.setCurrency(entity.getCurrency());
        dto.setMerchantId(entity.getMerchantId());
        dto.setMerchantName(entity.getMerchantName());
        dto.setLocation(entity.getLocation());
        dto.setTransactionType(entity.getTransactionType());
        dto.setTimestamp(entity.getTimestamp());
        dto.setIpAddress(entity.getIpAddress());
        dto.setDeviceId(entity.getDeviceId());
        dto.setCardNumber(entity.getCardNumber());
        dto.setOnline(entity.isOnline());
        return dto;
    }
}