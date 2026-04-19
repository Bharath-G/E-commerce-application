package com.learning.paymentservice.service;

import com.learning.paymentservice.model.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

   public boolean processPayment(OrderEvent orderEvent){
       log.info("Processing payment for orderId = {} amount = {}",
               orderEvent.orderId(), orderEvent.amount());

       //simulate payment gateway call
       //In real life: call Gpay, Card etc.,
       if(orderEvent.amount() <=0){
           log.warn("Invalid amount for orderId = {}", orderEvent.orderId());
       }

       //simulate occasional payment failure (10% chance)
       if(Math.random() < 0.1){
           log.warn("Payment gateway rejected orderId = {}", orderEvent.orderId());
           return false;
       }

       log.info("Payment successful for orderId = {}", orderEvent.orderId());
        return true;
   }
}
