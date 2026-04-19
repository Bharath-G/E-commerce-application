package com.learning.paymentservice.service;

import com.learning.paymentservice.model.PaymentEvent;
import org.springframework.stereotype.Service;

@Service
public interface PaymentEventProducer {
    void publishPaymentResult(PaymentEvent paymentEvent);
}
