package com.frauddetection.realtime.controller;

import com.frauddetection.realtime.dto.TransactionDto;
import com.frauddetection.realtime.producer.TransactionProducer;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionProducer transactionProducer;

    @PostMapping
    public ResponseEntity<String> submitTransaction(@Valid @RequestBody TransactionDto transaction) {
        try {
            logger.info("Received transaction submission: {}", transaction.getTransactionId());
            transactionProducer.sendTransaction(transaction);
            return ResponseEntity.ok("Transaction submitted for processing: " + transaction.getTransactionId());
        } catch (Exception e) {
            logger.error("Error submitting transaction: {}", transaction.getTransactionId(), e);
            return ResponseEntity.internalServerError()
                    .body("Error processing transaction: " + e.getMessage());
        }
    }
}