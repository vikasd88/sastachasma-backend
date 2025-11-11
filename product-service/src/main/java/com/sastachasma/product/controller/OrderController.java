package com.sastachasma.product.controller;

import com.sastachasma.product.dto.OrderDTO;
import com.sastachasma.product.dto.request.PlaceOrderRequest;
import com.sastachasma.product.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody PlaceOrderRequest request) {
        return new ResponseEntity<>(
                orderService.placeOrder(userId, request),
                HttpStatus.CREATED);
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderDTO> getOrder(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable String orderNumber) {
        return ResponseEntity.ok(orderService.getOrder(userId, orderNumber));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getUserOrders(
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }

    @PutMapping("/{orderNumber}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable String orderNumber,
            @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderNumber, status));
    }

    @PostMapping("/{orderNumber}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable String orderNumber) {
        orderService.cancelOrder(userId, orderNumber);
    }
}
