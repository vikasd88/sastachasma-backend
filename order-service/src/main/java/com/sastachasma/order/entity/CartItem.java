package com.sastachasma.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
    
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "image_url")
    private String imageUrl;

    // Lens details
    private Long lensId;
    private String lensType;
    private String lensMaterial;
    private String lensPrescriptionRange;
    private String lensCoating;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "lens_price", nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal lensPrice = BigDecimal.ZERO;
    
    @Transient
    public BigDecimal getTotalPrice() {
        BigDecimal total = unitPrice != null ? unitPrice : BigDecimal.ZERO;
        if (lensPrice != null) {
            total = total.add(lensPrice);
        }
        return total.multiply(BigDecimal.valueOf(quantity));
    }
}
