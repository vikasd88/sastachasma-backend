package com.sastachasma.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal lensPrice;
    private String imageUrl;

    // Lens details
    private Long lensId;
    private String lensType;
    private String lensMaterial;
    private String lensPrescriptionRange;
    private String lensCoating;

    private BigDecimal unitPrice;
}
