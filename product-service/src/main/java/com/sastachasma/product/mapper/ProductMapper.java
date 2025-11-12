package com.sastachasma.product.mapper;

import com.sastachasma.product.dto.ProductDTO;
import com.sastachasma.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product product);
    Product toEntity(ProductDTO productDTO);
    void updateProductFromDto(ProductDTO productDTO, @MappingTarget Product product);
}
