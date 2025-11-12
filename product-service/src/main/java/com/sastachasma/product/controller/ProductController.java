package com.sastachasma.product.controller;

import com.sastachasma.product.dto.ProductDTO;
import com.sastachasma.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product", description = "APIs for managing products")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get all products", 
              description = "Retrieves a list of all available products")
    @ApiResponse(responseCode = "200", 
               description = "Successfully retrieved list of products",
               content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))))
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Get product by ID", 
              description = "Retrieves a specific product by its ID")
    @ApiResponse(responseCode = "200", 
               description = "Successfully retrieved product",
               content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @ApiResponse(responseCode = "404", 
               description = "Product not found")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "ID of the product to be retrieved") 
            @PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Get products by category", 
              description = "Retrieves all products in a specific category")
    @ApiResponse(responseCode = "200", 
               description = "Successfully retrieved products by category",
               content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))))
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(
            @Parameter(description = "Category of products to retrieve") 
            @PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @Operation(summary = "Get active products", 
              description = "Retrieves all active products")
    @ApiResponse(responseCode = "200", 
               description = "Successfully retrieved active products",
               content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))))
    @GetMapping("/active")
    public ResponseEntity<List<ProductDTO>> getActiveProducts() {
        return ResponseEntity.ok(productService.getActiveProducts());
    }

    @Operation(summary = "Get products by frame material", 
              description = "Retrieves all products with a specific frame material")
    @ApiResponse(responseCode = "200", 
               description = "Successfully retrieved products by frame material",
               content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))))
    @GetMapping("/material/{material}")
    public ResponseEntity<List<ProductDTO>> getProductsByFrameMaterial(
            @Parameter(description = "Frame material to filter by") 
            @PathVariable String material) {
        return ResponseEntity.ok(productService.getProductsByFrameMaterial(material));
    }

    @Operation(summary = "Get products by gender", 
              description = "Retrieves all products for a specific gender")
    @ApiResponse(responseCode = "200", 
               description = "Successfully retrieved products by gender",
               content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))))
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<ProductDTO>> getProductsByGender(
            @Parameter(description = "Gender to filter by") 
            @PathVariable String gender) {
        return ResponseEntity.ok(productService.getProductsByGender(gender));
    }

    @Operation(summary = "Create a new product", 
              description = "Creates a new product with the provided details")
    @ApiResponse(responseCode = "201", 
               description = "Product created successfully",
               content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product details to be created",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProductDTO.class)))
            @Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing product", 
              description = "Updates an existing product with the provided details")
    @ApiResponse(responseCode = "200", 
               description = "Product updated successfully",
               content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @ApiResponse(responseCode = "404", 
               description = "Product not found")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @Parameter(description = "ID of the product to be updated") 
            @PathVariable Long id, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated product details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProductDTO.class)))
            @Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @Operation(summary = "Delete a product", 
              description = "Deletes a product by its ID")
    @ApiResponse(responseCode = "204", 
               description = "Product deleted successfully")
    @ApiResponse(responseCode = "404", 
               description = "Product not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID of the product to be deleted") 
            @PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
