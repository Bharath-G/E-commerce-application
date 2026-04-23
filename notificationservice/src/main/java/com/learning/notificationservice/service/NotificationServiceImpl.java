package com.learning.notificationservice.service;

import com.learning.events.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void processNotification(PaymentEvent paymentEvent) {
        log.info("Processing notification for orderId: {} | customerId: {}", 
                paymentEvent.orderId(), paymentEvent.customerId());
                
        if ("PAYMENT_SUCCESS".equals(paymentEvent.status())) {
            log.info("Sending SUCCESS email to customer {} for orderId: {}", 
                    paymentEvent.customerId(), paymentEvent.orderId());
        } else {
            log.warn("Sending FAILED payment alert to customer {} for orderId: {} Reason: {}", 
                    paymentEvent.customerId(), paymentEvent.orderId(), paymentEvent.failureReason());
        }
        
        log.info("Notification sent successfully for orderId: {}", paymentEvent.orderId());
    }
}
