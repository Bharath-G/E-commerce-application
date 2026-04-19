package com.learning.orderservice.service;

import com.learning.orderservice.model.OrderRequest;
import com.learning.orderservice.model.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderResponse createOrder(OrderRequest request, String txId);

    OrderRequest getOrder(String orderId);

    List<OrderRequest> getAllOrders();

    void updateStatus(String orderId, String status);

    void deleteOrder(String orderId);
}
