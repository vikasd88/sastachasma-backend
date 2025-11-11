package com.sastachasma.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "order_status_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "status_date", nullable = false)
    private ZonedDateTime statusDate;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();
    
    public String getStatus() {
        return this.status != null ? this.status.toUpperCase() : "PENDING";
    }
    
    public void setStatus(String status) {
        this.status = status != null ? status.toUpperCase() : "PENDING";
    }
    
    @PrePersist
    public void prePersist() {
        if (this.statusDate == null) {
            this.statusDate = ZonedDateTime.now();
        }
        if (this.status == null) {
            this.status = "PENDING";
        }
    }
}
