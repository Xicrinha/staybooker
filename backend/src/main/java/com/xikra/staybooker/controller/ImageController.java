package com.xikra.staybooker.controller;

import com.xikra.staybooker.domain.Image;
import com.xikra.staybooker.exceptions.NotFoundException;
import com.xikra.staybooker.mapper.ImageMapper;
import com.xikra.staybooker.model.ImageDTO;
import com.xikra.staybooker.service.ImageService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staybooker/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    private final ImageMapper imageMapper;

    @PostMapping
    public ResponseEntity<ImageDTO> createImage(@RequestBody @Valid ImageDTO image){
        Image createdImage = imageService.createImage(imageMapper.toEntity(image));
        return new ResponseEntity<>(imageMapper.toDTO(createdImage), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ImageDTO >> getAllImages(@RequestParam(required = false)Integer pageNumber,
                                                        @RequestParam(required = false)Integer pageSize){
        Page<ImageDTO> imageDTOPage = imageMapper.toDTOPage(imageService.getAllImages(pageNumber, pageSize));
        return new ResponseEntity<>(imageDTOPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDTO> getImageById(@PathVariable("id") Long id){
        ImageDTO imageDTO = imageService.getImageById(id)
                .map(imageMapper::toDTO)
                .orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(imageDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageDTO> updatedImage(@PathVariable("id") Long id, @RequestBody @Valid ImageDTO imageDTO){
        Image updatedImage = imageService.updateImage(id, imageMapper.toEntity(imageDTO));
        return new ResponseEntity<>(imageMapper.toDTO(updatedImage), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable("id") Long id){
        boolean deleted = imageService.deleteImage(id);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            throw new NotFoundException();
        }
    }
}
