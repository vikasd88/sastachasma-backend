package com.sastachasma.product.entity;

import com.sastachasma.product.entity.Product;
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(name = "price_at_addition", precision = 10, scale = 2)
    private BigDecimal priceAtAddition;
    
    @Column(name = "lens_price", precision = 10, scale = 2)
    private BigDecimal lensPrice = BigDecimal.ZERO;
    
    @Transient
    public BigDecimal getTotalPrice() {
        BigDecimal total = priceAtAddition != null ? priceAtAddition : BigDecimal.ZERO;
        if (lensPrice != null) {
            total = total.add(lensPrice);
        }
        return total.multiply(BigDecimal.valueOf(quantity));
    }
}
