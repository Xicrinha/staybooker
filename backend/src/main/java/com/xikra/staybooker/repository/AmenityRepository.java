package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity,Long> {
}
