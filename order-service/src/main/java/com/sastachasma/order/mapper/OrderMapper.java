package com.sastachasma.order.mapper;

import com.sastachasma.order.dto.OrderDTO;
import com.sastachasma.order.dto.OrderItemDTO;
import com.sastachasma.order.dto.OrderStatusHistoryDTO;
import com.sastachasma.order.entity.Order;
import com.sastachasma.order.entity.OrderItem;
import com.sastachasma.order.entity.OrderStatus;
import com.sastachasma.order.entity.OrderStatusHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;
    private final OrderStatusHistoryMapper statusHistoryMapper;

    @Autowired
    public OrderMapper(OrderItemMapper orderItemMapper, OrderStatusHistoryMapper statusHistoryMapper) {
        this.orderItemMapper = orderItemMapper;
        this.statusHistoryMapper = statusHistoryMapper;
    }

    public OrderDTO toDto(Order order) {
        if (order == null) {
            return null;
        }

        return OrderDTO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUserId())
                .items(order.getItems() != null ?
                       order.getItems().stream()
                           .map(orderItemMapper::toDto)
                           .collect(Collectors.toList()) :
                       null)
                .totalAmount(calculateTotalAmount(order))
                .status(order.getStatus() != null ? order.getStatus().name() : OrderStatus.PENDING.name())
                .shippingAddress(order.getShippingAddress())
                .billingAddress(order.getBillingAddress() != null && !order.getBillingAddress().isBlank() ?
                    order.getBillingAddress() :
                    order.getShippingAddress()) // Ensure billing address is mapped correctly
                .orderDate(order.getOrderDate())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .paymentTransactionId(order.getPaymentTransactionId())
                .build();
    }

    private BigDecimal calculateLensPrice(Order order) {
        if (order.getItems() == null) {
            return BigDecimal.ZERO;
        }
        return order.getItems().stream()
                .map(OrderItem::getLensPrice)
                .filter(price -> price != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order toEntity(OrderDTO dto) {
        if (dto == null) {
            return null;
        }

        Order order = Order.builder()
                .orderNumber(dto.getOrderNumber())
                .userId(dto.getUserId())
                .customerName(dto.getCustomerName())
                .orderDate(dto.getOrderDate() != null ? dto.getOrderDate() : LocalDateTime.now()) // Changed from ZonedDateTime
                .shippingAddress(dto.getShippingAddress())
                .billingAddress(dto.getBillingAddress() != null && !dto.getBillingAddress().isBlank() ?
                    dto.getBillingAddress() :
                    dto.getShippingAddress())
                .paymentMethod(dto.getPaymentMethod())
                .paymentStatus(dto.getPaymentStatus() != null ?
                    dto.getPaymentStatus().toUpperCase() :
                    "PENDING")
                .paymentTransactionId(dto.getPaymentTransactionId())
                .status(dto.getStatus() != null ? Order.OrderStatus.valueOf(dto.getStatus().toUpperCase()) : Order.OrderStatus.PENDING)
                .build();

        // Set items if present
        if (dto.getItems() != null) {
            order.setItems(dto.getItems().stream()
                    .map(orderItemMapper::toEntity)
                    .peek(item -> item.setOrder(order))
                    .collect(Collectors.toList()));
        }

        // Calculate subtotal if items are present
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            order.setSubtotal(calculateSubtotal(dto));
        }

        return order;
    }

    protected BigDecimal calculateTotalAmount(Order order) {
        if (order == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal subtotal = order.getSubtotal() != null ? order.getSubtotal() : BigDecimal.ZERO;
        BigDecimal shippingFee = order.getShippingFee() != null ? order.getShippingFee() : BigDecimal.ZERO;
        return subtotal.add(shippingFee);
    }

    protected BigDecimal calculateSubtotal(OrderDTO dto) {
        if (dto == null || dto.getItems() == null) {
            return BigDecimal.ZERO;
        }
        return dto.getItems().stream()
                .map(item -> {
                    BigDecimal itemTotal = item.getUnitPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));
                    if (item.getLensPrice() != null) {
                        itemTotal = itemTotal.add(item.getLensPrice());
                    }
                    return itemTotal;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    protected String getCurrentStatus(Order order) {
        if (order == null) {
            return "PENDING";
        }
        if (order.getStatus() != null) {
            return order.getStatus().name();
        }
        if (order.getStatusHistory() == null || order.getStatusHistory().isEmpty()) {
            return "PENDING";
        }
        return order.getStatusHistory().stream()
                .sorted((h1, h2) -> h2.getStatusDate().compareTo(h1.getStatusDate()))
                .findFirst()
                .map(OrderStatusHistory::getStatus)
                .orElse("PENDING");
    }

    // Additional helper methods for collections
    protected List<OrderItemDTO> mapOrderItems(List<OrderItem> items) {
        if (items == null) {
            return null;
        }
        return items.stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

    protected List<OrderStatusHistoryDTO> mapStatusHistory(List<OrderStatusHistory> statusHistory) {
        if (statusHistory == null) {
            return null;
        }
        return statusHistory.stream()
                .map(statusHistoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
