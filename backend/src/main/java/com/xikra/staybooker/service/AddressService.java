package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Address;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    Address createAddress(Address address);

    Page<Address> getAllAddress(String city, String state, Integer pageNumber, Integer pageSize);

    Optional<Address> getAddresById(Long id);

    Address updateAddress(Long id, Address address);

    boolean deleteAddress(Long id);

    Address addressPatch(Long id, Address address);

    Optional<Address> getAddressByZipcode(String zipcode);
}
