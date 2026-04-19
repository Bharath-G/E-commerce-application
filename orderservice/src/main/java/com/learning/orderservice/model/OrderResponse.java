package com.learning.orderservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("status")
    private String status;
}
