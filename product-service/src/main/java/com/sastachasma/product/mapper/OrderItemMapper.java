package com.sastachasma.product.mapper;

import com.sastachasma.product.dto.OrderItemDTO;
import com.sastachasma.product.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class OrderItemMapper {
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public OrderItem toEntity(OrderItemDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return OrderItem.builder()
                .id(dto.getId())
                .productId(dto.getProductId())
                .name(dto.getProductName())
                .price(dto.getUnitPrice())
                .quantity(dto.getQuantity())
                .lensPrice(dto.getLensPrice())
                .build();
    }

    public OrderItemDTO toDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProductId())
                .productName(orderItem.getName())
                .unitPrice(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .lensPrice(orderItem.getLensPrice())
                .priceAtPurchase(orderItem.getPrice())
                .totalPrice(calculateTotalPrice(orderItem))
                .createdAt(formatDate(orderItem.getCreatedAt()))
                .build();
    }

    private BigDecimal calculateTotalPrice(OrderItem orderItem) {
        if (orderItem == null || orderItem.getPrice() == null || orderItem.getQuantity() == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal total = orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
        if (orderItem.getLensPrice() != null) {
            total = total.add(orderItem.getLensPrice());
        }
        return total;
    }
    
    private String formatDate(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(formatter) : null;
    }
}
