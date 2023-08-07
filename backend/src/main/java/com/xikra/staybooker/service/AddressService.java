package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.exceptions.NotFoundException;
import com.xikra.staybooker.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> getAllAddress(String city, String state) {

        if(StringUtils.hasText(city) && !StringUtils.hasText(state)){
            return addressRepository.findByCity(city);
        }else if(!StringUtils.hasText(city) && StringUtils.hasText(state)){
            return addressRepository.findByState(state);
        }else if(StringUtils.hasText(city) && StringUtils.hasText(state)){
            return addressRepository.findByCityAndState(city, state);
        }else {
            return addressRepository.findAll();
        }
    }

    public Optional<Address> getAddresById(Long id) {
        return addressRepository.findById(id);
    }

    public Address updateAddress(Long id, Address address) {
        Address existingAddress = addressRepository.findById(id).orElseThrow(NotFoundException::new);
        existingAddress.setCity(address.getCity());
        existingAddress.setStreet(address.getStreet());
        existingAddress.setNumber(address.getNumber());
        existingAddress.setState(address.getState());
        existingAddress.setZipcode(address.getZipcode());

        return addressRepository.save(existingAddress);

    }

    public boolean deleteAddress(Long id) {
        if(addressRepository.existsById(id)){
            addressRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Address addressPatch(Long id, Address address) {
        Address existingAddress = addressRepository.findById(id).orElseThrow(NotFoundException::new);
        if (StringUtils.hasText(address.getNumber())){
            existingAddress.setNumber(address.getNumber());
        }

        if (StringUtils.hasText(address.getStreet())){
            existingAddress.setStreet(address.getStreet());
        }

        if (StringUtils.hasText(address.getCity())){
            existingAddress.setCity(address.getCity());
        }

        if (StringUtils.hasText(address.getState())){
            existingAddress.setState(address.getState());
        }

        if (StringUtils.hasText(address.getZipcode())){
            existingAddress.setZipcode(address.getZipcode());
        }

        return addressRepository.save(existingAddress);
    }
}
