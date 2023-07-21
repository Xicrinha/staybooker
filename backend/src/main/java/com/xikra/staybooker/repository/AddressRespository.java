package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRespository extends JpaRepository<Address, Long> {
}
