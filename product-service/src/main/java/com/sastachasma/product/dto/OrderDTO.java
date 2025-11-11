package com.sastachasma.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sastachasma.product.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private String orderNumber;
    private Long userId;
    private List<OrderItemDTO> items = new ArrayList<>();
    private BigDecimal totalAmount;
    private String status;  // Changed from OrderStatus to String
    private String shippingAddress;
    private String billingAddress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime orderDate;  // Changed from LocalDateTime to ZonedDateTime
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime updatedAt;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentTransactionId;
    private BigDecimal lensPrice;

    /**
     * Alias for orderNumber to match frontend expectations
     * @return the order number
     */
    public String getOrderId() {
        return this.orderNumber;
    }
}
