package com.sastachasma.product.service.impl;

import com.sastachasma.product.repository.ProductRepository;
import com.sastachasma.product.service.FilterService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterServiceImpl implements FilterService {

    private final ProductRepository productRepository;

    public FilterServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<String> getAllBrands() {
        return productRepository.findDistinctBrands();
    }

    @Override
    public List<String> getAllShapes() {
        return productRepository.findDistinctShapes();
    }

    @Override
    public List<String> getAllColors() {
        return productRepository.findDistinctColors();
    }

    @Override
    public List<String> getAllMaterials() {
        return productRepository.findDistinctFrameMaterials()
                .stream()
                .filter(material -> material != null && !material.trim().isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllLensTypes() {
        return productRepository.findDistinctLensTypes()
                .stream()
                .filter(type -> type != null && !type.trim().isEmpty())
                .collect(Collectors.toList());
    }
}
