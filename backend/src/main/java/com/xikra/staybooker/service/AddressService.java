package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.repository.AddressRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRespository addressRespository;
    public Address createAddress(Address address) {
        return addressRespository.save(address);
    }

    public List<Address> getAllAddress() {
        return addressRespository.findAll();
    }

    public Address getAddresById(Long id) {
        return addressRespository.findById(id).orElse(null);
    }

    public Address updateAddress(Long id, Address address) {
        Address existingAddress = addressRespository.findById(id).orElse(null);
        if(existingAddress != null){
            existingAddress.setCity(address.getCity());
            existingAddress.setStreet(address.getStreet());
            existingAddress.setNumber(address.getNumber());
            existingAddress.setState(address.getState());
            existingAddress.setZipcode(address.getZipcode());

            return addressRespository.save(existingAddress);
        }
        return null;
    }

    public boolean deleteAddress(Long id) {
        if(addressRespository.existsById(id)){
            addressRespository.deleteById(id);
            return true;
        }
        return false;
    }

    public Address addressPatch(Long id, Address address) {
        Address existingAddress = addressRespository.findById(id).orElse(null);
        if(existingAddress != null){
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

            return addressRespository.save(existingAddress);
        }
        return null;
    }
}
