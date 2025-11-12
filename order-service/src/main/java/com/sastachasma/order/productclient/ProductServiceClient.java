package com.sastachasma.order.productclient;

import com.sastachasma.order.productclient.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @PutMapping("/products/{productId}/stock")
    ResponseEntity<Void> updateProductStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity
    );

    @GetMapping("/products/{productId}")
    Optional<ProductDto> getProductById(@PathVariable Long productId);
}
