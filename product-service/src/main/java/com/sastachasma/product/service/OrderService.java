package com.sastachasma.product.service;

import com.sastachasma.product.dto.OrderDTO;
import com.sastachasma.product.dto.request.PlaceOrderRequest;

import java.util.List;

public interface OrderService {
    OrderDTO placeOrder(Long userId, PlaceOrderRequest request);
    OrderDTO getOrder(Long userId, String orderNumber);
    List<OrderDTO> getUserOrders(Long userId);
    OrderDTO updateOrderStatus(String orderNumber, String status);
    void cancelOrder(Long userId, String orderNumber);
}
