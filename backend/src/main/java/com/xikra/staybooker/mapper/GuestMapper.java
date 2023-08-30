package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Guest;
import com.xikra.staybooker.model.GuestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

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

    default Page<GuestDTO> toDTOPage(Page<Guest> guestPage){
        List<GuestDTO> guestDTOList = new ArrayList<>();

        for (Guest guest: guestPage){
            guestDTOList.add(toDTO(guest));
        }

        return new PageImpl<>(guestDTOList);
    }

    default Page<Guest> toEntityPage(Page<GuestDTO> guestDTOPage){
        List<Guest> guestList = new ArrayList<>();

        for (GuestDTO guestDTO : guestDTOPage){
            guestList.add(toEntity(guestDTO));
        }

        return new PageImpl<>(guestList);
    }
}
