package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByCity(String city);
    List<Address> findByState(String state);
    List<Address> findByCityAndState(String city, String state);
}
