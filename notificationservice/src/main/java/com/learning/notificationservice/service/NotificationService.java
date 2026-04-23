package com.learning.notificationservice.service;

import com.learning.events.PaymentEvent;

public interface NotificationService {
    void processNotification(PaymentEvent paymentEvent);
}
