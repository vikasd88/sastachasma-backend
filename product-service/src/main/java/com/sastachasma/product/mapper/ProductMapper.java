package com.sastachasma.product.mapper;

import com.sastachasma.product.dto.ProductDTO;
import com.sastachasma.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .color(product.getColor())
                .shape(product.getShape())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .inStock(product.getInStock())
                .category(product.getCategory())
                .frameMaterial(product.getFrameMaterial())
                .lensType(product.getLensType())
                .gender(product.getGender())
                .isActive(product.getIsActive())
                .build();
    }

    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .brand(productDTO.getBrand())
                .color(productDTO.getColor())
                .shape(productDTO.getShape())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .imageUrl(productDTO.getImageUrl())
                .inStock(productDTO.getInStock())
                .category(productDTO.getCategory())
                .frameMaterial(productDTO.getFrameMaterial())
                .lensType(productDTO.getLensType())
                .gender(productDTO.getGender())
                .isActive(productDTO.getIsActive() != null ? productDTO.getIsActive() : true)
                .build();
    }
}
