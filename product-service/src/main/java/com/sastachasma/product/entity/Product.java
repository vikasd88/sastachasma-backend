package com.sastachasma.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String brand;
    
    @Column
    private String color;
    
    @Column
    private String shape;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "in_stock")
    private Integer inStock;
    
    @Column(name = "category")
    private String category;
    
    @Column(name = "frame_material")
    private String frameMaterial;
    
    @Column(name = "lens_type")
    private String lensType;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating;
}
