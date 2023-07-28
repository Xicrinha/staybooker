package com.xikra.staybooker.controller;

import com.xikra.staybooker.domain.Amenity;
import com.xikra.staybooker.exceptions.NotFoundException;
import com.xikra.staybooker.mapper.AmenityMapper;
import com.xikra.staybooker.model.AmenityDTO;
import com.xikra.staybooker.service.AmenityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/staybooker/amenities")
@RequiredArgsConstructor
public class AmenityController {

    private final AmenityService amenityService;
    private final AmenityMapper amenityMapper;

    @PostMapping
    public ResponseEntity<AmenityDTO> createAmenity(@RequestBody @Valid AmenityDTO amenityDTO){
        Amenity createdAmenity = amenityService.createAmenity(amenityMapper.toEntity(amenityDTO));
        return new ResponseEntity<>(amenityMapper.toDTO(createdAmenity), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AmenityDTO>> getAllAmenities(){
        List<AmenityDTO> amenityDTOList = amenityService.getAllAmenities()
                .stream()
                .map(amenityMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(amenityDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenityDTO> getAmenityById(@PathVariable("id") Long id){
        AmenityDTO amenityDTO = amenityService.getAmenityById(id)
                .map(amenityMapper::toDTO)
                .orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(amenityDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AmenityDTO> updateAmenity(@RequestBody @Valid AmenityDTO amenityDTO, @PathVariable("id") Long id){
        Amenity updatedAmenity = amenityService.updateAmenity(amenityMapper.toEntity(amenityDTO), id);
        return new ResponseEntity<>(amenityMapper.toDTO(updatedAmenity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable("id") Long id){
        boolean deleted = amenityService.deleteAmenity(id);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            throw new NotFoundException();
        }
    }
}
