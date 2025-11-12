package com.sastachasma.lens.service.impl;

import com.sastachasma.lens.dto.LensDTO;
import com.sastachasma.lens.entity.Lens;
import com.sastachasma.lens.exception.ResourceNotFoundException;
import com.sastachasma.lens.mapper.LensMapper;
import com.sastachasma.lens.repository.LensRepository;
import com.sastachasma.lens.service.LensService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LensServiceImpl implements LensService {

    private final LensRepository lensRepository;
    private final LensMapper lensMapper;

    @Override
    public List<LensDTO> getAllLenses() {
        return lensRepository.findAll().stream()
                .map(lensMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LensDTO getLensById(Long id) {
        Lens lens = lensRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lens not found with id: " + id));
        return lensMapper.toDto(lens);
    }

    @Override
    public List<LensDTO> getLensesByType(String type) {
        return lensRepository.findByType(type).stream()
                .map(lensMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LensDTO> getLensesByMaterial(String material) {
        return lensRepository.findByMaterial(material).stream()
                .map(lensMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LensDTO> getActiveLenses() {
        return lensRepository.findByIsActiveTrue().stream()
                .map(lensMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LensDTO createLens(LensDTO lensDTO) {
        Lens lens = lensMapper.toEntity(lensDTO);
        Lens savedLens = lensRepository.save(lens);
        return lensMapper.toDto(savedLens);
    }

    @Override
    public LensDTO updateLens(Long id, LensDTO lensDTO) {
        Lens existingLens = lensRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lens not found with id: " + id));
        lensMapper.updateLensFromDto(lensDTO, existingLens);
        Lens updatedLens = lensRepository.save(existingLens);
        return lensMapper.toDto(updatedLens);
    }

    @Override
    public void deleteLens(Long id) {
        Lens lens = lensRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lens not found with id: " + id));
        lensRepository.delete(lens);
    }

    @Override
    @Transactional
    public void updateLensStock(Long lensId, Integer quantity) {
        Lens lens = lensRepository.findById(lensId)
                .orElseThrow(() -> new ResourceNotFoundException("Lens not found with id: " + lensId));
        lens.setInStock(lens.getInStock() + quantity);
        lensRepository.save(lens);
    }
}
