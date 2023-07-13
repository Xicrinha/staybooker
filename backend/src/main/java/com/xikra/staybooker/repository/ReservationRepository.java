package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
