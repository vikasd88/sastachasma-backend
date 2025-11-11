package com.sastachasma.product.mapper;

import com.sastachasma.product.dto.CartDTO;
import com.sastachasma.product.dto.CartItemDTO;
import com.sastachasma.product.entity.Cart;
import com.sastachasma.product.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CartMapper {

    public static final CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    public CartDTO toDto(Cart cart) {
        if (cart == null) {
            return null;
        }

        // Calculate the total price by summing up all item total prices
        BigDecimal calculatedTotalPrice = BigDecimal.ZERO;
        if (cart.getItems() != null && !cart.getItems().isEmpty()) {
            calculatedTotalPrice = cart.getItems().stream()
                    .map(CartItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        CartDTO.CartDTOBuilder cartDTO = CartDTO.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .totalPrice(calculatedTotalPrice) // Use the calculated total price
                .isActive(cart.getIsActive());

        if (cart.getItems() != null) {
            // Map items to DTOs and ensure lens price is included in the total
            List<CartItemDTO> itemDTOs = cart.getItems().stream()
                    .map(this::toCartItemDTO)
                    .peek(item -> {
                        // Ensure each item's total price includes lens price
                        if (item.getLensPrice() != null) {
                            BigDecimal itemTotal = item.getPriceAtAddition().add(item.getLensPrice())
                                    .multiply(BigDecimal.valueOf(item.getQuantity()));
                            item.setTotalPrice(itemTotal);
                        }
                    })
                    .collect(Collectors.toList());
            
            cartDTO.items(itemDTOs);
        }

        return cartDTO.build();
    }

    public CartItemDTO toCartItemDTO(CartItem item) {
        if (item == null) {
            return null;
        }

        return CartItemDTO.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .priceAtAddition(item.getPriceAtAddition())
                .lensPrice(item.getLensPrice())
                .quantity(item.getQuantity())
                .totalPrice(item.getTotalPrice())
                .imageUrl(item.getProduct() != null ? item.getProduct().getImageUrl() : null)
                .build();
    }
}
