package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.exceptions.NotFoundException;
import com.xikra.staybooker.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {


    private final AddressRepository addressRepository;

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public Page<Address> getAllAddress(String city, String state, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Address> page;

        if(StringUtils.hasText(city) && !StringUtils.hasText(state)){
            page = addressRepository.findAllByCityIsLikeIgnoreCase(city, pageRequest);
        }else if(!StringUtils.hasText(city) && StringUtils.hasText(state)){
            page = addressRepository.findAllByState(state, pageRequest);
        }else if(StringUtils.hasText(city) && StringUtils.hasText(state)){
            page = addressRepository.findAllByCityIsLikeIgnoreCaseAndState(city, state, pageRequest);
        }else {
            page = addressRepository.findAll(pageRequest);
        }

        return page;
    }

    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize){
        int queryPageNumber;
        int queryPageSize;

        if(pageNumber != null && pageNumber >0){
            queryPageNumber = pageNumber -1;
        }else{
            queryPageNumber = DEFAULT_PAGE;
        }

        if(pageSize == null){
            queryPageSize = DEFAULT_PAGE_SIZE;
        }else {
            if(pageSize > 1000){
                queryPageSize = 1000;
            }else {
                queryPageSize = pageSize;
            }
        }

        return PageRequest.of(queryPageNumber, queryPageSize);
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
