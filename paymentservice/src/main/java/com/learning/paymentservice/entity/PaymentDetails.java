package com.learning.paymentservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="PAYMENTDETAILS")
@Data
public class PaymentDetails {
    @Id
    @Column(name = "PAYMENT_ID")
    private String paymentId;
    @Column(name="PAYMENT_METHOD")
    private String paymentMethod;
    @Column(name="ORDER_ID")
    private String orderId;
    @Column(name="PAYMENT_STATUS")
    private String paymentStatus;
}
