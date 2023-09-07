package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Guest;
import com.xikra.staybooker.exceptions.NotFoundException;
import com.xikra.staybooker.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService{
    private final GuestRepository guestRepository;

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;
    public Guest createGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    public Page<Guest> getAllGuests(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        return guestRepository.findAll(pageRequest);
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

    public Optional<Guest> getGuestById(Long id){
        return guestRepository.findById(id);
    }

    public Guest updateGuest(Guest guest, Long id) {
        Guest existingGuest = guestRepository.findById(id).orElseThrow(NotFoundException::new);
        existingGuest.setFirstname(guest.getFirstname());
        existingGuest.setLastname(guest.getLastname());
        existingGuest.getAddress().setState(guest.getAddress().getState());
        existingGuest.getAddress().setStreet(guest.getAddress().getStreet());
        existingGuest.getAddress().setNumber(guest.getAddress().getNumber());
        existingGuest.getAddress().setCity(guest.getAddress().getCity());
        existingGuest.getAddress().setZipcode(guest.getAddress().getZipcode());
        existingGuest.setPhoneNumber(guest.getPhoneNumber());
        existingGuest.setEmail(guest.getEmail());

        return guestRepository.save(existingGuest);
    }

    public boolean deleteGuest(Long id) {
        if(guestRepository.existsById(id)){
            guestRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Guest guestPatch(Long id, Guest guest) {
        Guest existingGuest = guestRepository.findById(id).orElseThrow(NotFoundException::new);
        if(StringUtils.hasText(guest.getFirstname())){
            existingGuest.setFirstname(guest.getFirstname());
        }

        if(StringUtils.hasText(guest.getLastname())){
            existingGuest.setLastname(guest.getLastname());
        }

        if(StringUtils.hasText(guest.getAddress().getStreet())){
            existingGuest.getAddress().setStreet(guest.getAddress().getStreet());
        }

        if(StringUtils.hasText(guest.getAddress().getNumber())){
            existingGuest.getAddress().setNumber(guest.getAddress().getNumber());
        }

        if(StringUtils.hasText(guest.getAddress().getCity())){
            existingGuest.getAddress().setCity(guest.getAddress().getCity());
        }

        if(StringUtils.hasText(guest.getAddress().getState())){
            existingGuest.getAddress().setState(guest.getAddress().getState());
        }

        if(StringUtils.hasText(guest.getAddress().getZipcode())){
            existingGuest.getAddress().setZipcode(guest.getAddress().getZipcode());
        }

        if(StringUtils.hasText(guest.getPhoneNumber())){
            existingGuest.setPhoneNumber(guest.getPhoneNumber());
        }

        if(StringUtils.hasText(guest.getEmail())){
            existingGuest.setEmail(guest.getEmail());
        }

        return guestRepository.save(existingGuest);
    }

    @Override
    public Optional<Guest> findByAddressZipcode(String zipcode) {
        return guestRepository.findByAddressZipcode(zipcode);
    }
}
