package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.model.AddressDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AddressMapper {

    AddressDTO toDTO(Address address);

    Address toEntity(AddressDTO addressDTO);

    default Page<AddressDTO> toDTOPage(Page<Address> addressPage){
        List<AddressDTO> addressDTOList = new ArrayList<>();

        for (Address address: addressPage){
            addressDTOList.add(toDTO(address));
        }

        return new PageImpl<>(addressDTOList);
    }

    default Page<Address> toEntityPage(Page<AddressDTO> addressDTOPage){
        List<Address> addressList = new ArrayList<>();

        for (AddressDTO addressDTO : addressDTOPage){
            addressList.add(toEntity(addressDTO));
        }

        return new PageImpl<>(addressList);
    }
}
