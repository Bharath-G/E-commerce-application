package com.learning.paymentservice.service;

import com.learning.events.OrderEvent;
import com.learning.paymentservice.entity.PaymentDetails;
import com.learning.paymentservice.repository.PaymentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentsRepository paymentsRepository;

    public boolean processPayment(OrderEvent orderEvent){
        log.info("Processing payment for orderId = {} amount = {}",
                orderEvent.orderId(), orderEvent.amount());

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPaymentId(UUID.randomUUID().toString());
        paymentDetails.setOrderId(orderEvent.orderId());
        paymentDetails.setPaymentMethod("CREDIT_CARD");

        //simulate payment gateway call
        //In real life: call Gpay, Card etc.,
        if(orderEvent.amount() <=0){
            log.warn("Invalid amount for orderId = {}", orderEvent.orderId());
            paymentDetails.setPaymentStatus("FAILED");
            paymentsRepository.save(paymentDetails);
            return false;
        }

        //simulate occasional payment failure (10% chance)
        if(Math.random() < 0.1){
            log.warn("Payment gateway rejected orderId = {}", orderEvent.orderId());
            paymentDetails.setPaymentStatus("FAILED");
            paymentsRepository.save(paymentDetails);
            return false;
        }

        log.info("Payment successful for orderId = {}", orderEvent.orderId());
        paymentDetails.setPaymentStatus("SUCCESS");
        paymentsRepository.save(paymentDetails);
        return true;
    }
}
