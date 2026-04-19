package com.learning.orderservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequest {

    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("orderName")
    private String orderName;
    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("orderPrice")
    private Double orderPrice;
    @JsonProperty("customerDetailsRequest")
    private CustomerDetailsRequest customerDetailsRequest;
    @JsonProperty("status")
    private String status;

}
