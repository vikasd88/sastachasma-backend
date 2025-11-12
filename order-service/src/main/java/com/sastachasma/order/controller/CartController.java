package com.sastachasma.order.controller;

import com.sastachasma.order.dto.CartDTO;
import com.sastachasma.order.dto.request.AddToCartRequest;
import com.sastachasma.order.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDTO> getCart(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PostMapping("/items")
    public ResponseEntity<CartDTO> addToCart(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody AddToCartRequest request) {
        return new ResponseEntity<>(
                cartService.addToCart(userId, request),
                HttpStatus.CREATED);
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartDTO> updateCartItem(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(userId, itemId, quantity));
    }

    @DeleteMapping("/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromCart(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable Long itemId) {
        cartService.removeFromCart(userId, itemId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(@RequestHeader("X-User-Id") String userId) {
        cartService.clearCart(userId);
    }
}
