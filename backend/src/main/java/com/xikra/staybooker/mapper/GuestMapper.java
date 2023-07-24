package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Guest;
import com.xikra.staybooker.model.GuestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface GuestMapper {

    @Mapping(target = "street" , source = "address.street")
    @Mapping(target = "number" , source = "address.number")
    @Mapping(target = "city" , source = "address.city")
    @Mapping(target = "state" , source = "address.state")
    @Mapping(target = "zipcode" , source = "address.zipcode")
    GuestDTO toDTO (Guest guest);

    @Mapping(target = "address.street" , source = "street")
    @Mapping(target = "address.number" , source = "number")
    @Mapping(target = "address.city" , source = "city")
    @Mapping(target = "address.state" , source = "state")
    @Mapping(target = "address.zipcode" , source = "zipcode")
    Guest toEntity(GuestDTO guestDTO);
}
