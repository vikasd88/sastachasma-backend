package com.sastachasma.product.mapper;

import com.sastachasma.product.dto.LensDto;
import com.sastachasma.product.entity.Lens;
import org.springframework.stereotype.Component;

@Component
public class LensMapper {
    public LensDto toDto(Lens lens) {
        if (lens == null) {
            return null;
        }
        
        LensDto dto = new LensDto();
        dto.setId(lens.getId());
        dto.setType(lens.getType());
        dto.setPrice(lens.getPrice());
        dto.setDescription(lens.getDescription());
        return dto;
    }

    public Lens toEntity(LensDto dto) {
        if (dto == null) {
            return null;
        }
        
        Lens lens = new Lens();
        lens.setId(dto.getId());
        lens.setType(dto.getType());
        lens.setPrice(dto.getPrice());
        lens.setDescription(dto.getDescription());
        return lens;
    }
}
