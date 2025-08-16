package com.frauddetection.realtime.producer;

import com.frauddetection.realtime.dto.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TransactionProducer {

    private static final Logger logger = LoggerFactory.getLogger(TransactionProducer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${fraud-detection.topics.transactions}")
    private String transactionsTopic;

    public void sendTransaction(TransactionDto transaction) {
        try {
            CompletableFuture<SendResult<String, Object>> future = 
                    kafkaTemplate.send(transactionsTopic, transaction.getTransactionId(), transaction);
            
            future.whenComplete((result, exception) -> {
                if (exception == null) {
                    logger.debug("Transaction sent successfully: {} with offset: {}", 
                            transaction.getTransactionId(), 
                            result.getRecordMetadata().offset());
                } else {
                    logger.error("Failed to send transaction: {}", transaction.getTransactionId(), exception);
                }
            });
        } catch (Exception e) {
            logger.error("Error sending transaction to Kafka: {}", transaction.getTransactionId(), e);
        }
    }
}