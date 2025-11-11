package com.sastachasma.product.service;

import com.sastachasma.product.dto.LensDto;
import com.sastachasma.product.entity.Lens;
import com.sastachasma.product.mapper.LensMapper;
import com.sastachasma.product.repository.LensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LensService {
    
    private final LensRepository lensRepository;
    private final LensMapper lensMapper;
    
    @Autowired
    public LensService(LensRepository lensRepository, LensMapper lensMapper) {
        this.lensRepository = lensRepository;
        this.lensMapper = lensMapper;
    }
    
    public List<LensDto> getAllLenses() {
        return lensRepository.findAll().stream()
                .map(lensMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public LensDto getLensById(Long id) {
        return lensRepository.findById(id)
                .map(lensMapper::toDto)
                .orElse(null);
    }
    
    public LensDto createLens(LensDto lensDto) {
        Lens lens = lensMapper.toEntity(lensDto);
        Lens savedLens = lensRepository.save(lens);
        return lensMapper.toDto(savedLens);
    }
    
    public void deleteLens(Long id) {
        lensRepository.deleteById(id);
    }
}
