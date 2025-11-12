package com.sastachasma.order.mapper;

import com.sastachasma.order.dto.OrderItemDTO;
import com.sastachasma.order.entity.OrderItem;
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
                .imageUrl(orderItem.getImageUrl())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .lensId(orderItem.getLensId() != null ? String.valueOf(orderItem.getLensId()) : null)
                .lensType(orderItem.getLensType())
                .lensMaterial(orderItem.getLensMaterial())
                .lensPrescriptionRange(orderItem.getLensPrescriptionRange())
                .lensCoating(orderItem.getLensCoating())
                .lensPrice(orderItem.getLensPrice())
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
}
