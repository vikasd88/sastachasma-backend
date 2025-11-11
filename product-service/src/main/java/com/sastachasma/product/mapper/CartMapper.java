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

        CartDTO.CartDTOBuilder cartDTO = CartDTO.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .totalPrice(cart.getTotalPrice())
                .isActive(cart.getIsActive());

        if (cart.getItems() != null) {
            cartDTO.items(cart.getItems().stream()
                    .map(this::toCartItemDTO)
                    .collect(Collectors.toList()));
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
