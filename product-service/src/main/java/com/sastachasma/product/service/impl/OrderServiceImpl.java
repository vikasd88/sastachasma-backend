package com.sastachasma.product.service.impl;

import com.sastachasma.product.dto.OrderDTO;
import com.sastachasma.product.dto.request.PlaceOrderRequest;
import com.sastachasma.product.entity.*;
import com.sastachasma.product.exception.ResourceNotFoundException;
import com.sastachasma.product.mapper.OrderMapper;
import com.sastachasma.product.repository.*;
import com.sastachasma.product.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDTO placeOrder(Long userId, PlaceOrderRequest request) {
        // Create order
        Order order = new Order();
        order.setOrderNumber(request.getOrderId());
        order.setUserId(userId);
        order.setStatus(Order.OrderStatus.PROCESSING);
        order.setOrderDate(ZonedDateTime.now());
        order.setEstimatedDelivery(ZonedDateTime.parse(request.getEstimatedDelivery()));
        order.setCustomerName(request.getCustomerName());
        
        // Set addresses
        order.setShippingAddress(convertAddressToJson(request.getShippingAddress()));
        
        // Set payment info
        order.setPaymentMethod(request.getPayment().getMethod());
        order.setPaymentStatus(request.getPayment().getStatus());
        order.setPaymentTransactionId(request.getPayment().getTransactionId());
        
        // Set totals
        order.setSubtotal(request.getSubtotal() != null ? request.getSubtotal() : BigDecimal.ZERO);
        order.setShippingFee(request.getShippingFee() != null ? request.getShippingFee() : BigDecimal.ZERO);
        order.setTax(request.getTax() != null ? request.getTax() : BigDecimal.ZERO);
        order.setTotalAmount(request.getTotal() != null ? request.getTotal() : BigDecimal.ZERO);

        // Create order items
        for (PlaceOrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(itemRequest.getProductId());
            orderItem.setName(itemRequest.getName());
            orderItem.setPrice(itemRequest.getPrice());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setImageUrl(itemRequest.getImageUrl());
            orderItem.setLensId(itemRequest.getLensId());
            orderItem.setLensName(itemRequest.getLensName());
            orderItem.setLensPrice(itemRequest.getLensPrice());
            orderItem.setFrameSize(itemRequest.getFrameSize());
            
            order.getItems().add(orderItem);
        }
        
        // Save status history
        if (request.getStatusHistory() != null && !request.getStatusHistory().isEmpty()) {
            for (PlaceOrderRequest.StatusHistory statusHistory : request.getStatusHistory()) {
                OrderStatusHistory history = new OrderStatusHistory();
                history.setOrder(order);
                history.setStatus(statusHistory.getStatus());
                history.setStatusDate(ZonedDateTime.now());
                history.setDescription(statusHistory.getDescription());
                order.getStatusHistory().add(history);
            }
        } else {
            // Add default status history
            OrderStatusHistory history = new OrderStatusHistory();
            history.setOrder(order);
            history.setStatus("PROCESSING");
            history.setStatusDate(ZonedDateTime.now());
            history.setDescription("Order received and is being processed.");
            order.getStatusHistory().add(history);
        }

        // Save order
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }
    
    private String convertAddressToJson(PlaceOrderRequest.Address address) {
        return String.format(
            "{\"name\":\"%s\",\"street\":\"%s\",\"city\":\"%s\",\"state\":\"%s\",\"pincode\":\"%s\",\"phone\":\"%s\"}",
            address.getName() != null ? address.getName().replace("\"", "\\\"") : "",
            address.getStreet() != null ? address.getStreet().replace("\"", "\\\"") : "",
            address.getCity() != null ? address.getCity().replace("\"", "\\\"") : "",
            address.getState() != null ? address.getState().replace("\"", "\\\"") : "",
            address.getPincode() != null ? address.getPincode().replace("\"", "\\\"") : "",
            address.getPhone() != null ? address.getPhone().replace("\"", "\\\"") : ""
        );
    }

    @Override
    public OrderDTO getOrder(Long userId, String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with number: " + orderNumber));
        
        if (!order.getUserId().equals(userId)) {
            throw new SecurityException("You are not authorized to view this order");
        }
        
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDTO> getUserOrders(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO updateOrderStatus(String orderNumber, String status) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with number: " + orderNumber));
        
        try {
            Order.OrderStatus newStatus = Order.OrderStatus.valueOf(status.toUpperCase());
            order.setStatus(newStatus);
order.setUpdatedAt(ZonedDateTime.now());
            return orderMapper.toDto(orderRepository.save(order));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }
    }

    @Override
    @Transactional
    public void cancelOrder(Long userId, String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with number: " + orderNumber));
        
        if (!order.getUserId().equals(userId)) {
            throw new SecurityException("You are not authorized to cancel this order");
        }
        
        if (order.getStatus() == Order.OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already cancelled");
        }
        
        if (order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel an already delivered order");
        }
        
        // Return items to stock
        for (OrderItem item : order.getItems()) {
            productRepository.findById(item.getProductId()).ifPresent(product -> {
                product.setInStock(product.getInStock() + item.getQuantity());
                productRepository.save(product);
            });
        }
        
        order.setStatus(Order.OrderStatus.CANCELLED);
        order.setUpdatedAt(ZonedDateTime.now());
        orderRepository.save(order);
    }
    
    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
