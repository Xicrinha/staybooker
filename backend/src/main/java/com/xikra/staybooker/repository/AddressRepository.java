package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Page<Address> findByCity(String city);
    Page<Address> findByState(String state);
    Page<Address> findByCityAndState(String city, String state);
}
