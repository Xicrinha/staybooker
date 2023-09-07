package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Guest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface GuestService {

    public Guest createGuest(Guest guest);

    public Page<Guest> getAllGuests(Integer pageNumber, Integer pageSize);

    public Optional<Guest> getGuestById(Long id);

    public Guest updateGuest(Guest guest, Long id);

    public boolean deleteGuest(Long id);

    public Guest guestPatch(Long id, Guest guest);

    public Optional<Guest> findByAddressZipcode(String zipcode);
}
