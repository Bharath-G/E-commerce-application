package com.learning.paymentservice.service;

import com.learning.events.OrderEvent;
import com.learning.events.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final PaymentService paymentService;
    private final PaymentEventProducer paymentEventProducer;

    @KafkaListener(
            topics = "order-events",
            groupId = "payment-service",
            concurrency = "3" //3 threads for 3 partitions in parallel
    )
    public void handleOrderEvent(
            @Payload OrderEvent orderEvent,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment ack
            ){
        log.info("Received OrderEvent -> orderId = {} | partition = {} | offset = {}",
                orderEvent.orderId(), partition, offset);

        try{
            //Step 1 - process payment
            boolean success = paymentService.processPayment(orderEvent);
            //Step 2 - build result event
            PaymentEvent paymentEvent = new PaymentEvent(
                    UUID.randomUUID().toString(),
                    orderEvent.orderId(),
                    orderEvent.customerId(),
                    orderEvent.amount(),
                    success ? "PAYMENT_SUCCESS" : "PAYMENT_FAILED",
                    success ? null : "Payment gateway rejected"
            );
            //Step 3 - publish result to payment-events
            paymentEventProducer.publishPaymentResult(paymentEvent);
            //Step 4 - commit offset only after everything succeeded
            ack.acknowledge();
            log.info("Order {} processed -> status = {}",
                    orderEvent.orderId(), paymentEvent.status());
        }catch (Exception e){
            log.error("Failed to process orderId = {} - will retry. Error due to {}",
                    orderEvent.orderId(),e.getMessage());
        }
    }

    //Handles messages that failed after all retries
    @KafkaListener(
            topics = "order-events.DLT",
            groupId = "payment-dlt-handler"
    )
    public void handleDLT(
            @Payload OrderEvent orderEvent,
            @Header(KafkaHeaders.EXCEPTION_MESSAGE) String errorMessage
    ){
        log.error("DLT : orderId = {} failed permanently. Reason = {}",
                orderEvent.orderId(), errorMessage);
        //alert team, write to error DB, trigger manual review
    }
}
