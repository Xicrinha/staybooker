package com.xikra.staybooker.controller;

import com.xikra.staybooker.domain.Amenity;
import com.xikra.staybooker.mapper.AmenityMapper;
import com.xikra.staybooker.model.AmenityDTO;
import com.xikra.staybooker.service.AmenityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staybooker/amenities")
@RequiredArgsConstructor
public class AmenityController {

    private final AmenityService amenityService;
    private final AmenityMapper amenityMapper;

    @PostMapping
    public ResponseEntity<AmenityDTO> createAmenity(@RequestBody @Valid AmenityDTO amenityDTO){
        Amenity createdAmenity = AmenityService.createAmenity(amenityMapper.toEntity(amenityDTO));
        return new ResponseEntity<>(amenityMapper.toDTO(createdAmenity), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AmenityDTO>> getAllAmenities(){
        List<AmenityDTO> amenityDTOList = amenityService.getAllAmenities()
                .stream()
                .map(amenityMapper::toDTO)
                .toList();
        return new ResponseEntity<>(amenityDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenityDTO> getAmenityById(@PathVariable Long id){
        Amenity amenity = amenityService.getAmenityById(id);
        if(amenity != null){
            return new ResponseEntity<>(amenityMapper.toDTO(amenity), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AmenityDTO> updateAmenity(@RequestBody @Valid AmenityDTO amenityDTO, @PathVariable Long id){
        Amenity updatedAmenity = amenityService.updateAmenity(amenityMapper.toEntity(amenityDTO), id);
        if(updatedAmenity != null){
            return new ResponseEntity<>(amenityMapper.toDTO(updatedAmenity), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long id){
        boolean deleted = amenityService.deleteAmenity(id);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AmenityDTO> amenityPatch(@RequestBody @Valid AmenityDTO amenityDTO, @PathVariable Long id){
        Amenity updatedAmenity = amenityService.amenityPatch(amenityMapper.toEntity(amenityDTO), id);
        if(updatedAmenity != null){
            return new ResponseEntity<>(amenityMapper.toDTO(updatedAmenity), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
