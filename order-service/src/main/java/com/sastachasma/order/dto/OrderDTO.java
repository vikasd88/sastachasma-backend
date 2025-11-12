package com.sastachasma.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sastachasma.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime; // Changed from ZonedDateTime
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private String orderNumber;
    private String customerName;
    private String userId;
    @Builder.Default
    private List<OrderItemDTO> items = new ArrayList<>();
    private BigDecimal totalAmount;
    private String status;  // Changed from OrderStatus to String
    private String shippingAddress;
    private String billingAddress;
    // Removed @JsonFormat annotation
    private LocalDateTime orderDate;  // Changed from ZonedDateTime to LocalDateTime
    
    private String paymentMethod;
    private String paymentStatus;
    private String paymentTransactionId;
    
    /**
     * Alias for orderNumber to match frontend expectations
     * @return the order number
     */
    public String getOrderId() {
        return this.orderNumber;
    }
}
