package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Amenity;
import com.xikra.staybooker.model.AmenityDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AmenityMapper {

    AmenityDTO amenityToAmenityDTO(Amenity amenity);

    Amenity amenityDTOToAmenity(AmenityDTO amenityDTO);
}
