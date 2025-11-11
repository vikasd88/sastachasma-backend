package com.sastachasma.product.controller;

import com.sastachasma.product.dto.LensDto;
import com.sastachasma.product.service.LensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lenses")
public class LensController {
    
    private final LensService lensService;
    
    @Autowired
    public LensController(LensService lensService) {
        this.lensService = lensService;
    }
    
    @GetMapping
    public ResponseEntity<List<LensDto>> getAllLenses() {
        return ResponseEntity.ok(lensService.getAllLenses());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LensDto> getLensById(@PathVariable Long id) {
        LensDto lens = lensService.getLensById(id);
        if (lens != null) {
            return ResponseEntity.ok(lens);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<LensDto> createLens(@RequestBody LensDto lensDto) {
        return ResponseEntity.ok(lensService.createLens(lensDto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLens(@PathVariable Long id) {
        lensService.deleteLens(id);
        return ResponseEntity.noContent().build();
    }
}
