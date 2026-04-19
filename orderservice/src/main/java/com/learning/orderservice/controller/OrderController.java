package com.learning.orderservice.controller;

import com.learning.orderservice.model.OrderEvent;
import com.learning.orderservice.model.OrderRequest;
import com.learning.orderservice.model.OrderResponse;
import com.learning.orderservice.service.OrderEventProducer;
import com.learning.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecommerce/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 1. Create OrderRequest
    @PostMapping("/create")
    public OrderResponse createOrder(
            @RequestBody OrderRequest request,
            @RequestHeader(value = "Correlation-ID") String txId) {

        return orderService.createOrder(request, txId);
    }

    // 2. Get OrderRequest by ID
    @GetMapping("/{orderId}")
    public OrderRequest getOrder(@PathVariable String orderId) {
        return (orderService.getOrder(orderId));
    }

    // 3. Get All Orders
    @GetMapping
    public List<OrderRequest> getAllOrders() {
        return (orderService.getAllOrders());
    }

    // 4. Update Status
    @PutMapping("/{orderId}/status")
    public String updateStatus(
            @PathVariable String orderId,
            @RequestParam String status) {

        orderService.updateStatus(orderId, status);
        return ("Status updated");
    }

    // 5. Delete OrderRequest
    @DeleteMapping("/{orderId}")
    public String deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return ("Deleted successfully");
    }
}
