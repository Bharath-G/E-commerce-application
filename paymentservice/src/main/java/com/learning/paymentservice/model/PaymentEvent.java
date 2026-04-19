package com.learning.paymentservice.model;

// events/PaymentEvent.java
// This is the event Payment Service PRODUCES for Notification Service
public record PaymentEvent(
    String paymentId,
    String orderId,
    String customerId,
    double amount,
    String status,         // "PAYMENT_SUCCESS" or "PAYMENT_FAILED"
    String failureReason   // null if success
) {}