package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Amenity;
import com.xikra.staybooker.exceptions.NotFoundException;
import com.xikra.staybooker.repository.AmenityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService{

    private final AmenityRepository amenityRepository;

    public Amenity createAmenity(Amenity amenity) {
        return amenityRepository.save(amenity);
    }

    public List<Amenity> getAllAmenities() {
        return amenityRepository.findAll();
    }

    public Optional<Amenity> getAmenityById(Long id) {
        return amenityRepository.findById(id);
    }

    public Amenity updateAmenity(Amenity amenity, Long id) {
        Amenity existingAmenity = amenityRepository.findById(id).orElseThrow(NotFoundException::new);
        existingAmenity.setName(amenity.getName());
        return amenityRepository.save(existingAmenity);
    }

    public boolean deleteAmenity(Long id) {
        if(amenityRepository.existsById(id)){
            amenityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
