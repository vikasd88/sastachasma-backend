package com.sastachasma.lens.mapper;

import com.sastachasma.lens.dto.LensDTO;
import com.sastachasma.lens.entity.Lens;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LensMapper {
    LensDTO toDto(Lens lens);
    Lens toEntity(LensDTO lensDTO);
    void updateLensFromDto(LensDTO lensDTO, @MappingTarget Lens lens);
}
