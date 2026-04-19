package com.learning.orderservice.model;

// This is your "message body" — same as a REST request body
public record OrderEvent(
    String orderId,
    String customerId,
    double amount,
    String status
) {}