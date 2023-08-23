package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Amenity;

import java.util.List;
import java.util.Optional;

public interface AmenityService {

    public Amenity createAmenity(Amenity amenity);

    public List<Amenity> getAllAmenities();

    public Optional<Amenity> getAmenityById(Long id);

    public Amenity updateAmenity(Amenity amenity, Long id);

    public boolean deleteAmenity(Long id);
}
