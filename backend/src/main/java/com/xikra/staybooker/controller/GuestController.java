package com.xikra.staybooker.controller;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.domain.Guest;
import com.xikra.staybooker.mapper.GuestMapper;
import com.xikra.staybooker.model.AddressDTO;
import com.xikra.staybooker.model.GuestDTO;
import com.xikra.staybooker.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staybooker/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;
    private final GuestMapper guestMapper;

    @PostMapping
    public ResponseEntity<GuestDTO> createGuest(@RequestBody @Valid GuestDTO guestDTO){
        Guest createdGuest = guestService.createGuest(guestMapper.toEntity(guestDTO));
        return new ResponseEntity<>(guestMapper.toDTO(createdGuest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GuestDTO>> getAllGuests(){
        List<GuestDTO> guestDTOList = guestService.getAllGuests()
                .stream()
                .map(guestMapper::toDTO)
                .toList();
        return new ResponseEntity<>(guestDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> getGuestById(@PathVariable Long id){
        Guest guest = guestService.getGuestById(id);
        if(guest != null){
            return new ResponseEntity<>(guestMapper.toDTO(guest), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuestDTO> updateGuest(@RequestBody @Valid GuestDTO guestDTO,@PathVariable Long id){
        Guest updatedGuest = guestService.updateGuest(guestMapper.toEntity(guestDTO), id);
        if(updatedGuest != null){
            return new ResponseEntity<>(guestMapper.toDTO(updatedGuest), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id){
        boolean deleted = guestService.deleteGuest(id);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GuestDTO> guestPatch(@PathVariable Long id, @RequestBody GuestDTO guestDTO){
        Guest updatedGuest = guestService.addressPatch(id, guestMapper.toEntity(guestDTO));
        if(updatedGuest != null){
            return new ResponseEntity<>(guestMapper.toDTO(updatedGuest), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
