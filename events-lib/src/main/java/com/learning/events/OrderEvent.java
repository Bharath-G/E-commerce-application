package com.learning.events;

public record OrderEvent(
    String orderId,
    String customerId,
    double amount,
    String status
) {}
