package com.learning.paymentservice.service;

import com.learning.events.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentEventProducerImpl implements PaymentEventProducer {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public void publishPaymentResult(PaymentEvent paymentEvent){
        kafkaTemplate
                .send("payment-events", paymentEvent.orderId(), paymentEvent)
                .whenComplete((result, ex)->{
                    if(ex != null){
                        log.error("Failed to publish payment result for the orderId = {} : {}",
                                paymentEvent.orderId(), ex.getMessage());
                    }
                    else{
                        log.info("Payment result published for orderId = {} | status = {} -> partition = {} | offset = {}",
                                paymentEvent.orderId(),
                                paymentEvent.status(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}
