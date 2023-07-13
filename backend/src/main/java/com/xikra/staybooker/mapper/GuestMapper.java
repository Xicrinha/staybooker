package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Guest;
import com.xikra.staybooker.model.GuestDTO;
import org.mapstruct.Mapper;

@Mapper
public interface GuestMapper {

    GuestDTO guestToGuestDTO (Guest guest);

    Guest guestDTOToGuest(GuestDTO guestDTO);
}
