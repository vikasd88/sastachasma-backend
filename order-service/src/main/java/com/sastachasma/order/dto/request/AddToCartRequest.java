package com.sastachasma.order.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartRequest {
    @Positive(message = "Product ID must be positive")
    private Long productId;
    private String name;
    private BigDecimal price;
    private String imageUrl;

    private Long lensId;
    private String lensType;
    private String lensMaterial;
    private String lensPrescriptionRange;
    private String lensCoating;

    @Positive(message = "Quantity must be positive")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    
    @DecimalMin(value = "0.0", message = "Lens price must be a positive number")
    @Builder.Default
    private BigDecimal lensPrice = BigDecimal.ZERO;
}
