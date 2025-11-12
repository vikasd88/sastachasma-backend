package com.sastachasma.order.service.impl;

import com.sastachasma.order.dto.CartDTO;
import com.sastachasma.order.dto.request.AddToCartRequest;
import com.sastachasma.order.entity.Cart;
import com.sastachasma.order.entity.CartItem;
import com.sastachasma.order.exception.ResourceNotFoundException;
import com.sastachasma.order.lensclient.LensDto;
import com.sastachasma.order.lensclient.LensServiceClient;
import com.sastachasma.order.mapper.CartMapper;
import com.sastachasma.order.productclient.ProductDto;
import com.sastachasma.order.productclient.ProductServiceClient;
import com.sastachasma.order.repository.CartItemRepository;
import com.sastachasma.order.repository.CartRepository;
import com.sastachasma.order.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final ProductServiceClient productServiceClient;
    private final LensServiceClient lensServiceClient;

    @Override
    @Transactional
    public CartDTO getOrCreateCart(String userId) {
        Optional<Cart> existingCart = cartRepository.findByUserId(userId);
        return existingCart.map(cart -> {
            cart.getItems().size(); // Force initialization of items collection for existing cart
            return cartMapper.toDto(cart);
        }).orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .userId(userId)
                            .isActive(true)
                            .items(new ArrayList<>()) // Ensure items list is initialized
                            .build();
                    Cart savedCart = cartRepository.save(newCart);
                    savedCart.getItems().size(); // Force initialization of items collection
                    return cartMapper.toDto(savedCart);
                });
    }

    @Override
    @Transactional
    public CartDTO addToCart(String userId, AddToCartRequest addToCartRequest) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> createNewCart(userId));

        ProductDto product = productServiceClient.getProductById(addToCartRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + addToCartRequest.getProductId()));

        LensDto lens = null;
        if (addToCartRequest.getLensId() != null) {
            lens = lensServiceClient.getLensById(addToCartRequest.getLensId())
                    .orElseThrow(() -> new ResourceNotFoundException("Lens not found with ID: " + addToCartRequest.getLensId()));
        }
        
        final LensDto finalLens = lens;

        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(product.getId()))
                .filter(item -> {
                    if (item.getLensId() != null && finalLens != null) {
                        return item.getLensId().equals(finalLens.getId());
                    } else return item.getLensId() == null && finalLens == null;
                })
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + addToCartRequest.getQuantity());
            cartItemRepository.save(cartItem);
        } else {
            CartItem newCartItem = CartItem.builder()
                    .cart(cart)
                    .productId(product.getId())
                    .name(product.getName())
                    .imageUrl(product.getImageUrl())
                    
                    .unitPrice(product.getPrice())
                    .quantity(addToCartRequest.getQuantity())
                    .lensId(lens != null ? lens.getId() : null)
                    .lensType(lens != null ? lens.getType() : null)
                    .lensMaterial(lens != null ? lens.getMaterial() : null)
                    .lensPrescriptionRange(lens != null ? lens.getPrescriptionRange() : null)
                    .lensCoating(lens != null ? lens.getCoating() : null)
                    .lensPrice(lens != null ? lens.getPrice() : BigDecimal.ZERO)
                    .build();
            cart.getItems().add(newCartItem);
            cartItemRepository.save(newCartItem);
        }

        updateCartTotalPrice(cart);
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public CartDTO updateCartItem(String userId, Long itemId, Integer quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

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

        updateCartTotalPrice(cart);
        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public void removeFromCart(String userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

        CartItem item = cart.getItems().stream()
                .filter(cartItem -> cartItem.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));

        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        updateCartTotalPrice(cart);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    @Override
    public CartDTO getCart(String userId) {
        return getOrCreateCart(userId); // Replaced direct find with getOrCreateCart
    }

    private Cart createNewCart(String userId) {
        Cart newCart = Cart.builder()
                .userId(userId)
                .items(new ArrayList<>()) // Initialize items list
                .totalPrice(BigDecimal.ZERO) // Initialize total price
                .isActive(true) // Set to active by default
                .build();
        return cartRepository.save(newCart);
    }

    private void updateCartTotalPrice(Cart cart) {
        BigDecimal total = cart.getItems().stream()
                .map(item -> item.getUnitPrice().add(item.getLensPrice()).multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(total);
    }
}
