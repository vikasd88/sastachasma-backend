package com.sastachasma.product.entity;

public enum OrderStatus {
    PENDING,        // Order has been placed but not yet confirmed
    CONFIRMED,      // Order has been confirmed by the system
    PROCESSING,     // Order is being processed
    SHIPPED,        // Order has been shipped
    OUT_FOR_DELIVERY, // Order is out for delivery
    DELIVERED,      // Order has been delivered
    CANCELLED,      // Order has been cancelled
    REFUNDED,       // Order has been refunded
    FAILED;         // Order processing failed

    public static OrderStatus fromString(String value) {
        if (value == null) {
            return PENDING;  // Default to PENDING if null
        }
        try {
            return OrderStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Log the error if needed
            return PENDING;  // Default to PENDING for any invalid status
        }
    }
}
