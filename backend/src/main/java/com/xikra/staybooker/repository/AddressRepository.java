package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Page<Address> findAllByCityIsLikeIgnoreCase(String city, PageRequest pageRequest);
    Page<Address> findAllByState(String state, PageRequest pageRequest);
    Page<Address> findAllByCityIsLikeIgnoreCaseAndState(String city, String state, PageRequest pageRequest);
}
