package com.learning.notificationservice.service;

import com.learning.events.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "payment-events",
            groupId = "notification-service",
            concurrency = "3" 
    )
    public void handlePaymentEvent(
            @Payload PaymentEvent paymentEvent,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment ack
            ){
        log.info("Received PaymentEvent -> orderId = {} | partition = {} | offset = {}",
                paymentEvent.orderId(), partition, offset);

        try{
            notificationService.processNotification(paymentEvent);
            ack.acknowledge();
            log.info("Notification processing completed for orderId = {}", paymentEvent.orderId());
        }catch (Exception e){
            log.error("Failed to process notification for orderId = {} - will retry. Error due to {}",
                    paymentEvent.orderId(),e.getMessage());
            // Implicitly not acknowledging will cause the error handler to retry, and finally send to DLT
            throw e;
        }
    }

    //Handles messages that failed after all retries
    @KafkaListener(
            topics = "payment-events.DLT",
            groupId = "notification-dlt-handler"
    )
    public void handleDLT(
            @Payload PaymentEvent paymentEvent,
            @Header(KafkaHeaders.EXCEPTION_MESSAGE) String errorMessage
    ){
        log.error("DLT : Notification for orderId = {} failed permanently. Reason = {}",
                paymentEvent.orderId(), errorMessage);
    }
}
