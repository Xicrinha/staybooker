package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Image;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ImageService {

    Image createImage(Image image);

    Page<Image> getAllImages(Integer pageNumber, Integer pageSize);

    Optional<Image> getImageById(Long id);

    Image updateImage(Long id, Image image);

    boolean deleteImage(Long id);

}
