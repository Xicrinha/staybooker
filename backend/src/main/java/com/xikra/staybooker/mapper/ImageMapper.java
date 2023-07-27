package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Image;
import com.xikra.staybooker.model.ImageDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ImageMapper {

    ImageDTO toDTO(Image image);

    Image toEntity(ImageDTO imageDTO);
}
