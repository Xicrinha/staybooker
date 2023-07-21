package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.model.AddressDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    AddressDTO toDTO(Address address);

    Address toEntity(AddressDTO addressDTO);

}
