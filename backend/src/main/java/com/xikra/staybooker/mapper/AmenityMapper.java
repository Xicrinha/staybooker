package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Amenity;
import com.xikra.staybooker.model.AmenityDTO;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AmenityMapper {

    AmenityDTO toDTO(Amenity amenity);

    Amenity toEntity(AmenityDTO amenityDTO);

    default List<Amenity> toEntityList(List<AmenityDTO> amenityDTOList){
        List<Amenity> amenityList = new ArrayList<>();

        for(AmenityDTO amenityDTO : amenityDTOList){
            amenityList.add(toEntity(amenityDTO));
        }

        return amenityList;
    }

    default List<AmenityDTO> toDTOList(List<Amenity> amenityList){
        List<AmenityDTO> amenityDTOList = new ArrayList<>();

        for(Amenity amenity : amenityList){
            amenityDTOList.add(toDTO(amenity));
        }

        return amenityDTOList;
    }
}
