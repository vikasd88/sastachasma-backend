package com.sastachasma.order.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceOrderRequest {
    
    @NotBlank(message = "Customer name is required")
    private String customerName;
    
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
    
    @NotNull(message = "Billing address is required")
    private BillingAddress billingAddress;
    
    @NotEmpty(message = "Order items are required")
    private List<@Valid OrderItemRequest> items;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemRequest {
        @NotNull(message = "Product ID is required")
        private Long productId;
        private String name;
        private BigDecimal unitPrice;
        private String imageUrl;
        private Long lensId;
        private String lensType;
        private String lensMaterial;
        private String lensPrescriptionRange;
        private String lensCoating;
        private BigDecimal lensPrice;
        @NotNull(message = "Quantity is required")
        private Integer quantity;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BillingAddress {
        @NotBlank(message = "Street is required")
        private String street;
        @NotBlank(message = "City is required")
        private String city;
        @NotBlank(message = "State is required")
        private String state;
        @NotBlank(message = "Postal Code is required")
        private String postalCode;
        @NotBlank(message = "Country is required")
        private String country;
        @NotBlank(message = "Phone number is required")
        private String phone;
    }
}
