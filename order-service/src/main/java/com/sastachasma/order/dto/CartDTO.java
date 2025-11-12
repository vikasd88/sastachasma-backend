package com.sastachasma.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {
    private Long id;
    private Long userId;
    @Builder.Default
    private List<CartItemDTO> items = new ArrayList<>();
    private BigDecimal totalPrice;
    private Boolean isActive;
}
