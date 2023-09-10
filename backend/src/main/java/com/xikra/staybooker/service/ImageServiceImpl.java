package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Image;
import org.springframework.data.domain.Page;

import java.util.Optional;

public class ImageServiceImpl implements ImageService{

    @Override
    public Image createImage(Image image) {
        return null;
    }

    @Override
    public Page<Image> getAllImages(Integer pageNumber, Integer pageSize) {
        return null;
    }

    @Override
    public Optional<Image> getImageById(Long id) {
        return Optional.empty();
    }

    @Override
    public Image updateImage(Long id, Image image) {
        return null;
    }

    @Override
    public boolean deleteImage(Long id) {
        return false;
    }
}
