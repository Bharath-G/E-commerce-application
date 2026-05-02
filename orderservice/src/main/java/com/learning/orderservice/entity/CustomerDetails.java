package com.learning.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "customerdetails")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetails {

    @Id
    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="MIDDLE_NAME")
    private String middleName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="COUNTRY_CODE")
    private String countryCode;

    @Column(name="PHONE_NUMBER")
    private Integer phoneNumber;

    @Column(name="ADDRESS_LINE1")
    private String addressLine1;

    @Column(name="ADDRESS_LINE2")
    private String addressLine2;

    @Column(name="STATE")
    private String state;

    @Column(name = "COUNTRY")
    private String country;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
}
