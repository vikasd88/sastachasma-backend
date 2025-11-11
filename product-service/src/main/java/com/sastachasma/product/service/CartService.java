package com.sastachasma.product.service;

import com.sastachasma.product.dto.CartDTO;
import com.sastachasma.product.dto.request.AddToCartRequest;

public interface CartService {
    CartDTO getOrCreateCart(Long userId);
    CartDTO addToCart(Long userId, AddToCartRequest request);
    CartDTO updateCartItem(Long userId, Long itemId, Integer quantity);
    void removeFromCart(Long userId, Long itemId);
    void clearCart(Long userId);
    CartDTO getCart(Long userId);
}
