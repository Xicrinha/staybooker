package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Amenity;
import com.xikra.staybooker.repository.AmenityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenityService {

    private final AmenityRepository amenityRepository;

    public Amenity createAmenity(Amenity amenity) {
        return amenityRepository.save(amenity);
    }

    public List<Amenity> getAllAmenities() {
        return amenityRepository.findAll();
    }

    public Amenity getAmenityById(Long id) {
        return amenityRepository.findById(id).orElse(null);
    }

    public Amenity updateAmenity(Amenity amenity, Long id) {
        Amenity existingAmenity = amenityRepository.findById(id).orElse(null);
        if(existingAmenity != null){
            existingAmenity.setName(amenity.getName());
            return amenityRepository.save(existingAmenity);
        }
        return null;
    }

    public boolean deleteAmenity(Long id) {
        if(amenityRepository.existsById(id)){
            amenityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
