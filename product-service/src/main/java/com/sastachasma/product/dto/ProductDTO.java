package com.sastachasma.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private String color;
    private String shape;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer inStock;
    private String category;
    private String frameMaterial;
    private String lensType;
    private String gender;
    private Boolean isActive;
}
