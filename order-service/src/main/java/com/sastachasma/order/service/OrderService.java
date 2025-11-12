package com.sastachasma.order.service;

import com.sastachasma.order.dto.OrderDTO;
import com.sastachasma.order.dto.request.PlaceOrderRequest;

import java.util.List;

public interface OrderService {
    OrderDTO placeOrder(String userId, PlaceOrderRequest request);
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(Long id);
    OrderDTO getOrder(String userId, String orderNumber);
    List<OrderDTO> getUserOrders(String userId);
    OrderDTO updateOrderStatus(String orderNumber, String status);
    void cancelOrder(String userId, String orderNumber);
}
