package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Amenity;
import com.xikra.staybooker.model.AmenityDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AmenityMapper {

    AmenityDTO toDTO(Amenity amenity);

    Amenity toEntity(AmenityDTO amenityDTO);
}
