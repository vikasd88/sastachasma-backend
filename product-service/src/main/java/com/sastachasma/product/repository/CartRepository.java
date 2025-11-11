package com.sastachasma.product.repository;

import com.sastachasma.product.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndIsActiveTrue(Long userId);
    boolean existsByUserIdAndIsActiveTrue(Long userId);
}
