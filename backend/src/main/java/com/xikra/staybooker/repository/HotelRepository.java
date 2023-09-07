package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel,Long> {

    Optional<Hotel> findByAddressZipcode(String zipcode);
}
