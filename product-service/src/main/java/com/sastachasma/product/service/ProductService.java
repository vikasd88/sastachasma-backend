package com.sastachasma.product.service;

import com.sastachasma.product.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    List<ProductDTO> getProductsByCategory(String category);
    List<ProductDTO> getActiveProducts();
    List<ProductDTO> getProductsByFrameMaterial(String frameMaterial);
    List<ProductDTO> getProductsByGender(String gender);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}
