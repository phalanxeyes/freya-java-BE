package com.salest.freya.mapper;

import com.salest.freya.dtos.cover.CoverDTO;
import com.salest.freya.dtos.cover.CreateCoverDTO;
import com.salest.freya.entities.Cover;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoverMapper {
    Cover createCoverDTOtoCover(CreateCoverDTO dto);
    CoverDTO coverToCoverDTO(Cover cover);
}
