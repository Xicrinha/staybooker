package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest,Long> {


}
