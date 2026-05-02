package com.learning.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orderdetails")
public class OrderDetails {
    @Id
    @Column(name="order_id")
    private String orderId;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "order_price")
    private Double orderPrice;

    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;
}
