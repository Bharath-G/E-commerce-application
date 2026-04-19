package com.learning.paymentservice.service;

import com.learning.paymentservice.model.OrderEvent;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    boolean processPayment(OrderEvent orderEvent);
}
