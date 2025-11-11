package com.sastachasma.product.repository;

import com.sastachasma.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByIsActiveTrue();
    List<Product> findByCategoryAndIsActiveTrue(String category);
    
    @Query("SELECT DISTINCT p.brand FROM Product p WHERE p.brand IS NOT NULL")
    List<String> findDistinctBrands();
    
    @Query("SELECT DISTINCT p.shape FROM Product p WHERE p.shape IS NOT NULL")
    List<String> findDistinctShapes();
    
    @Query("SELECT DISTINCT p.color FROM Product p WHERE p.color IS NOT NULL")
    List<String> findDistinctColors();
    
    @Query("SELECT DISTINCT p.frameMaterial FROM Product p WHERE p.frameMaterial IS NOT NULL")
    List<String> findDistinctFrameMaterials();
    
    @Query("SELECT DISTINCT p.lensType FROM Product p WHERE p.lensType IS NOT NULL")
    List<String> findDistinctLensTypes();
    List<Product> findByFrameMaterialAndIsActiveTrue(String frameMaterial);
    List<Product> findByGenderAndIsActiveTrue(String gender);
}
