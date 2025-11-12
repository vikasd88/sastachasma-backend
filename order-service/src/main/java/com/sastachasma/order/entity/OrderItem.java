package com.sastachasma.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal price = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private Integer quantity = 1;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    // Lens details
    @Column(name = "lens_id")
    private Long lensId;
    @Column(name = "lens_type")
    private String lensType;
    @Column(name = "lens_material")
    private String lensMaterial;
    @Column(name = "lens_prescription_range")
    private String lensPrescriptionRange;
    @Column(name = "lens_coating")
    private String lensCoating;
    @Column(name = "lens_price", precision = 10, scale = 2)
    private BigDecimal lensPrice;

    @Column(name = "frame_size")
    private String frameSize;
    
    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    @Builder.Default
    private BigDecimal unitPrice = BigDecimal.ZERO;
    
    @PrePersist
    @PreUpdate
    private void updatePriceFields() {
        // Ensure price is always in sync with unitPrice if not explicitly set
        if (this.price == null || this.price.compareTo(BigDecimal.ZERO) == 0) {
            this.price = this.unitPrice;
        } else if (this.unitPrice == null || this.unitPrice.compareTo(BigDecimal.ZERO) == 0) {
            this.unitPrice = this.price;
        }
    }
    
    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    @Builder.Default
    private BigDecimal totalPrice = BigDecimal.ZERO;
    
    @Column(name = "price_at_purchase", precision = 10, scale = 2, nullable = false)
    @Builder.Default
    private BigDecimal priceAtPurchase = BigDecimal.ZERO;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
}
