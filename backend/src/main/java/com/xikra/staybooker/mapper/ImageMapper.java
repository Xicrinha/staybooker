package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Image;
import com.xikra.staybooker.model.ImageDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ImageMapper {

    ImageDTO toDTO(Image image);

    Image toEntity(ImageDTO imageDTO);

    default Page<ImageDTO> toDTOPage(Page<Image> imagePage){
        List<ImageDTO> imageDTOList = new ArrayList<>();

        for(Image image : imagePage){
            imageDTOList.add(toDTO(image));
        }

        return new PageImpl<>(imageDTOList);
    }

    default Page<Image> toEntityPage(Page<ImageDTO> imageDTOPage){
        List<Image> imageList = new ArrayList<>();

        for(ImageDTO imageDTO : imageDTOPage){
            imageList.add(toEntity(imageDTO));
        }

        return new PageImpl<>(imageList);
    }
}
