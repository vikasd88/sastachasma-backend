package com.sastachasma.product.service.impl;

import com.sastachasma.product.dto.CartDTO;
import com.sastachasma.product.dto.CartItemDTO;
import com.sastachasma.product.dto.request.AddToCartRequest;
import com.sastachasma.product.entity.Cart;
import com.sastachasma.product.entity.CartItem;
import com.sastachasma.product.exception.ResourceNotFoundException;
import com.sastachasma.product.mapper.CartMapper;
import com.sastachasma.product.entity.Product;
import com.sastachasma.product.repository.CartItemRepository;
import com.sastachasma.product.repository.CartRepository;
import com.sastachasma.product.repository.ProductRepository;
import com.sastachasma.product.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public CartDTO getOrCreateCart(Long userId) {
        return cartRepository.findByUserIdAndIsActiveTrue(userId)
                .map(cartMapper::toDto)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .userId(userId)
                            .isActive(true)
                            .items(new ArrayList<>()) // Ensure items list is initialized
                            .build();
                    return cartMapper.toDto(cartRepository.save(newCart));
                });
    }

    @Override
    @Transactional
    public CartDTO addToCart(Long userId, AddToCartRequest request) {
        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .userId(userId)
                            .isActive(true)
                            .items(new ArrayList<>()) // Ensure items list is initialized
                            .build();
                    return cartRepository.save(newCart);
                });
                
        // Ensure items list is not null
        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // Update quantity and lens price if item already in cart
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            // Update lens price if provided in the request
            if (request.getLensPrice() != null) {
                item.setLensPrice(request.getLensPrice());
            }
        } else {
            // Add new item to cart
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .priceAtAddition(product.getPrice())
                    .lensPrice(request.getLensPrice() != null ? request.getLensPrice() : BigDecimal.ZERO)
                    .build();
            cart.getItems().add(newItem);
        }

        cart.calculateTotalPrice();
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public CartDTO updateCartItem(Long userId, Long itemId, Integer quantity) {
        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Active cart not found for user: " + userId));

        CartItem item = cart.getItems().stream()
                .filter(cartItem -> cartItem.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));

        if (quantity <= 0) {
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(quantity);
        }

        cart.calculateTotalPrice();
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public void removeFromCart(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Active cart not found for user: " + userId));

        CartItem item = cart.getItems().stream()
                .filter(cartItem -> cartItem.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));

        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        cart.calculateTotalPrice();
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Active cart not found for user: " + userId));
        
        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    @Override
    public CartDTO getCart(Long userId) {
        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Active cart not found for user: " + userId));
        return cartMapper.toDto(cart);
    }
}
