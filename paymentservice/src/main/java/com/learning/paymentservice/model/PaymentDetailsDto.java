package com.learning.paymentservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentDetailsDto {
    @JsonProperty("paymentId")
    private String paymentId;
    @JsonProperty("paymentMethod")
    private String paymentMethod;
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("paymentStatus")
    private String paymentStatus;

}
