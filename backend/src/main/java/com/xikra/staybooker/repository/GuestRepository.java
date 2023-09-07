package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest,Long> {

    Optional<Guest> findByAddressZipcode(String zipcode);
}
