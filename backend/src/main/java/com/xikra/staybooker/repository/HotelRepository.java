package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
