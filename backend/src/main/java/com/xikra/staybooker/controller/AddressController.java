package com.xikra.staybooker.controller;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.exceptions.NotFoundException;
import com.xikra.staybooker.mapper.AddressMapper;
import com.xikra.staybooker.model.AddressDTO;
import com.xikra.staybooker.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/staybooker/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@RequestBody @Valid AddressDTO addressDTO){
        Address createdAddress = addressService.createAddress(addressMapper.toEntity(addressDTO));
        return new ResponseEntity<>(addressMapper.toDTO(createdAddress), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAllAddress(){
        List<AddressDTO> addressDTOList = addressService.getAllAddress()
                .stream()
                .map(addressMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(addressDTOList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable("id") Long id){
        AddressDTO addressDTO = addressService.getAddresById(id)
                .map(addressMapper::toDTO)
                .orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable("id") Long id, @RequestBody @Valid AddressDTO addressDTO){
        Address updatedAddress = addressService.updateAddress(id, addressMapper.toEntity(addressDTO));
        return new ResponseEntity<>(addressMapper.toDTO(updatedAddress), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("id") Long id) {
        boolean deleted = addressService.deleteAddress(id);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            throw new NotFoundException();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AddressDTO> addressPatch(@PathVariable("id") Long id, @RequestBody AddressDTO addressDTO){
        Address updatedAddress = addressService.addressPatch(id, addressMapper.toEntity(addressDTO));
        return new ResponseEntity<>(addressMapper.toDTO(updatedAddress), HttpStatus.OK);
    }
}
