package com.sastachasma.product.repository;

import com.sastachasma.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByIsActiveTrue();
    List<Product> findByFrameMaterial(String frameMaterial);
    List<Product> findByGender(String gender);
}
