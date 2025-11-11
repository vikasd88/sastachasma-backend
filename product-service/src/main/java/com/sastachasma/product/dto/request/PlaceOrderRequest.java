package com.sastachasma.product.dto.request;

import com.sastachasma.product.dto.OrderItemDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceOrderRequest {
    @NotBlank(message = "Customer name is required")
    private String customerName;
    
    @NotBlank(message = "Order date is required")
    private String orderDate;
    
    @NotBlank(message = "Estimated delivery is required")
    private String estimatedDelivery;
    
    @NotBlank(message = "Order ID is required")
    private String orderId;
    
    @NotEmpty(message = "Order items are required")
    private List<@Valid OrderItemRequest> items;
    
    @NotNull(message = "Shipping address is required")
    private Address shippingAddress;
    
    @NotNull(message = "Payment information is required")
    private PaymentInfo payment;
    
    private List<StatusHistory> statusHistory;
    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal tax;
    private BigDecimal total;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemRequest {
        private Long productId;
        private String name;
        private BigDecimal price;
        private Integer quantity;
        private String imageUrl;
        private String lensId;
        private String lensName;
        private BigDecimal lensPrice;
        private String frameSize;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Address {
        private String name;
        private String street;
        private String city;
        private String state;
        private String pincode;
        private String phone;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaymentInfo {
        private String method;
        private String status;
        private BigDecimal amount;
        private String transactionId;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StatusHistory {
        private String status;
        private String date;
        private String description;
    }
}
