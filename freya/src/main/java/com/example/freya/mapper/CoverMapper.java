package com.example.freya.mapper;

import com.example.freya.dtos.cover.CoverDTO;
import com.example.freya.dtos.cover.CreateCoverDTO;
import com.example.freya.entities.Cover;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CoverMapper {
    Cover createCoverDTOtoCover(CreateCoverDTO dto);
    CoverDTO coverToCoverDTO(Cover cover);
}
