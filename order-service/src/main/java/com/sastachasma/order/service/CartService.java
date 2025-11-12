package com.sastachasma.order.service;

import com.sastachasma.order.dto.CartDTO;
import com.sastachasma.order.dto.request.AddToCartRequest;

public interface CartService {
    CartDTO getOrCreateCart(String userId);
    CartDTO addToCart(String userId, AddToCartRequest addToCartRequest);
    CartDTO updateCartItem(String userId, Long itemId, Integer quantity);
    void removeFromCart(String userId, Long itemId);
    void clearCart(String userId);
    CartDTO getCart(String userId);
}
