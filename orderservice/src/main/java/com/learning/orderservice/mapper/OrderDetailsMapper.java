package com.learning.orderservice.mapper;

import com.learning.orderservice.entity.OrderDetails;
import com.learning.orderservice.model.OrderRequest;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailsMapper {
    public OrderDetails mapOrderRequest(OrderRequest orderRequest){
        OrderDetails order = new OrderDetails();
        order.setOrderId(orderRequest.getOrderId());
        order.setOrderName(orderRequest.getOrderName());
        order.setCustomerId(orderRequest.getCustomerId());
        order.setOrderPrice(orderRequest.getOrderPrice());
        order.setStatus(orderRequest.getStatus());
        return order;
    }

    public OrderRequest mapToOrderRequest(OrderDetails orderDetails){
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(orderDetails.getOrderId());
        orderRequest.setOrderName(orderDetails.getOrderName());
        orderRequest.setCustomerId(orderDetails.getCustomerId());
        orderRequest.setOrderPrice(orderDetails.getOrderPrice());
        orderRequest.setStatus(orderDetails.getStatus());
        return orderRequest;
    }
}
