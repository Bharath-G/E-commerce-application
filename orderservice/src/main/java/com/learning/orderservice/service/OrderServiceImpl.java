package com.learning.orderservice.service;

import com.learning.orderservice.entity.CustomerDetails;
import com.learning.orderservice.entity.OrderDetails;
import com.learning.orderservice.mapper.CustomerDetailsMapper;
import com.learning.orderservice.mapper.OrderDetailsMapper;
import com.learning.orderservice.model.CustomerDetailsRequest;
import com.learning.orderservice.model.OrderEvent;
import com.learning.orderservice.model.OrderRequest;
import com.learning.orderservice.model.OrderResponse;
import com.learning.orderservice.repository.CustomerDetailsRepository;
import com.learning.orderservice.repository.OrderDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.List;

import static com.learning.orderservice.constants.OrderConstants.ORDER_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final CustomerDetailsMapper customerDetailsMapper;
    private final OrderDetailsMapper orderDetailsMapper;
    private final CustomerDetailsRepository customerDetailsRepository;
    private final OrderEventProducer orderEventProducer;

    @Override
    public OrderResponse createOrder(OrderRequest request, String txId) {
        MDC.put("Correlation-ID", txId != null ? txId : "N/A");
        MDC.put(ORDER_ID, request.getOrderId() != null ? request.getOrderId() : "N/A");
        log.info("Initiating order creation process for customer: {}", request.getCustomerId());
        
        OrderDetails orderDetails = orderDetailsMapper.mapOrderRequest(request);
        CustomerDetailsRequest customerDetailsRequest = request.getCustomerDetailsRequest();
        CustomerDetails customerDetails = customerDetailsMapper.mapCustomerDetails(customerDetailsRequest);
        
        orderDetailsRepository.save(orderDetails);
        customerDetailsRepository.save(customerDetails);

        log.info("Successfully persisted order and customer details");

        OrderResponse orderResponse = new OrderResponse(request.getOrderId(), request.getStatus());

        orderEventProducer.publishOrderCreated(
                new OrderEvent(
                        request.getOrderId(),
                        customerDetails.getCustomerId(),
                        request.getOrderPrice(),
                        "CREATED"
                )
        );
        return orderResponse;
    }

    @Override
    public OrderRequest getOrder(String orderId) {
        MDC.put(ORDER_ID, orderId);
        log.info("Fetching order details");
        OrderDetails orderDetails = orderDetailsRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order not found with id: {}", orderId);
                    return new RuntimeException("Order not found with id: " + orderId);
                });
        log.info("Order details retrieved successfully");
        return orderDetailsMapper.mapToOrderRequest(orderDetails);
    }

    @Override
    public List<OrderRequest> getAllOrders() {
        return orderDetailsRepository.findAll().stream()
                .map(orderDetailsMapper::mapToOrderRequest)
                .toList();
    }

    @Override
    public void updateStatus(String orderId, String status) {
        MDC.put(ORDER_ID, orderId);
        log.info("Updating order status to: {}", status);
        OrderDetails orderDetails = orderDetailsRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Failed to update status. Order not found with id: {}", orderId);
                    return new RuntimeException("Order not found with id: " + orderId);
                });
        orderDetails.setStatus(status);
        orderDetailsRepository.save(orderDetails);
        log.info("Order status updated securely");
    }

    @Override
    public void deleteOrder(String orderId) {
        MDC.put(ORDER_ID, orderId);
        log.info("Initiating deletion of order");
        orderDetailsRepository.deleteById(orderId);
        log.info("Order completely removed from database");
    }
}
