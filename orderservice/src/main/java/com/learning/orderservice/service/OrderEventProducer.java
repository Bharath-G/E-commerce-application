package com.learning.orderservice.service;


import com.learning.orderservice.model.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventProducer {
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void publishOrderCreated(OrderEvent orderEvent){
        kafkaTemplate
                .send("order-event",orderEvent.orderId(), orderEvent)
                .whenComplete((result, ex)->{
                    if(ex != null){
                        log.error("Failed to send order {}: {}", orderEvent.orderId(), ex.getMessage());
                    }else{
                        log.info("Order {} sent -> partition {}, offset {}",
                                orderEvent.orderId(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}
