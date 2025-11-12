package com.sastachasma.lens.service;

import com.sastachasma.lens.dto.LensDTO;

import java.util.List;

public interface LensService {
    List<LensDTO> getAllLenses();
    LensDTO getLensById(Long id);
    List<LensDTO> getLensesByType(String type);
    List<LensDTO> getLensesByMaterial(String material);
    List<LensDTO> getActiveLenses();
    LensDTO createLens(LensDTO lensDTO);
    LensDTO updateLens(Long id, LensDTO lensDTO);
    void deleteLens(Long id);
    void updateLensStock(Long lensId, Integer quantity);
}
