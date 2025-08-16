package com.frauddetection.realtime.consumer;

import com.frauddetection.realtime.dto.TransactionDto;
import com.frauddetection.realtime.service.FraudDetectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TransactionConsumer.class);

    @Autowired
    private FraudDetectionService fraudDetectionService;

    @KafkaListener(
            topics = "${fraud-detection.topics.transactions}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "transactionKafkaListenerContainerFactory"
    )
    public void consumeTransaction(@Payload TransactionDto transaction,
                                 @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                 @Header(KafkaHeaders.OFFSET) long offset,
                                 Acknowledgment acknowledgment) {
        
        logger.debug("Received transaction from topic: {}, partition: {}, offset: {}, transactionId: {}",
                topic, partition, offset, transaction.getTransactionId());

        try {
            fraudDetectionService.processTransaction(transaction);
            acknowledgment.acknowledge();
            logger.debug("Successfully processed transaction: {}", transaction.getTransactionId());
        } catch (Exception e) {
            logger.error("Error processing transaction: {}", transaction.getTransactionId(), e);
        }
    }
}