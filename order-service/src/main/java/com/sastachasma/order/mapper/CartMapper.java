package com.sastachasma.order.mapper;

import com.sastachasma.order.dto.CartDTO;
import com.sastachasma.order.dto.CartItemDTO;
import com.sastachasma.order.entity.Cart;
import com.sastachasma.order.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "userId", source = "userId")
    CartDTO toDto(Cart cart);
    @Mapping(target = "userId", source = "userId")
    Cart toEntity(CartDTO cartDTO);

    @Mapping(target = "productName", source = "name")
    @Mapping(target = "unitPrice", source = "unitPrice") // Direct mapping from unitPrice
    CartItemDTO toCartItemDTO(CartItem cartItem);
    
    @Mapping(target = "name", source = "productName")
    @Mapping(target = "unitPrice", source = "unitPrice") // Direct mapping to unitPrice
    @Mapping(target = "cart", ignore = true) // Ignore mapping of 'cart' property
    CartItem toCartItemEntity(CartItemDTO cartItemDTO);
}
