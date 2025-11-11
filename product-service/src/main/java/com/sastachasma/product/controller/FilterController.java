package com.sastachasma.product.controller;

import com.sastachasma.product.service.FilterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/filters")
public class FilterController {
    
    private final FilterService filterService;
    
    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }
    
    @GetMapping("/brands")
    public ResponseEntity<List<String>> getAllBrands() {
        return ResponseEntity.ok(filterService.getAllBrands());
    }
    
    @GetMapping("/shapes")
    public ResponseEntity<List<String>> getAllShapes() {
        return ResponseEntity.ok(filterService.getAllShapes());
    }
    
    @GetMapping("/colors")
    public ResponseEntity<List<String>> getAllColors() {
        return ResponseEntity.ok(filterService.getAllColors());
    }
    
    @GetMapping("/materials")
    public ResponseEntity<List<String>> getAllMaterials() {
        return ResponseEntity.ok(filterService.getAllMaterials());
    }
    
    @GetMapping("/lens-types")
    public ResponseEntity<List<String>> getAllLensTypes() {
        return ResponseEntity.ok(filterService.getAllLensTypes());
    }
}
