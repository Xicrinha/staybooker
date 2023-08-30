package com.xikra.staybooker.controller;

import com.xikra.staybooker.domain.Guest;
import com.xikra.staybooker.exceptions.NotFoundException;
import com.xikra.staybooker.mapper.GuestMapper;
import com.xikra.staybooker.model.GuestDTO;
import com.xikra.staybooker.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<Page<GuestDTO>> getAllGuests(@RequestParam(required = false) Integer pageNumber,
                                                       @RequestParam(required = false) Integer pageSize){
        Page<GuestDTO> guestDTOList = guestMapper.toDTOPage(guestService.getAllGuests(pageNumber,pageSize));
        return new ResponseEntity<>(guestDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> getGuestById(@PathVariable("id") Long id){
        GuestDTO guestDTO = guestService.getGuestById(id)
                .map(guestMapper::toDTO).orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(guestDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuestDTO> updateGuest(@RequestBody @Valid GuestDTO guestDTO,@PathVariable("id") Long id){
        Guest updatedGuest = guestService.updateGuest(guestMapper.toEntity(guestDTO), id);
        return new ResponseEntity<>(guestMapper.toDTO(updatedGuest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable("id") Long id){
        boolean deleted = guestService.deleteGuest(id);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            throw new NotFoundException();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GuestDTO> guestPatch(@PathVariable("id") Long id, @RequestBody GuestDTO guestDTO){
        Guest updatedGuest = guestService.guestPatch(id, guestMapper.toEntity(guestDTO));
        return new ResponseEntity<>(guestMapper.toDTO(updatedGuest), HttpStatus.OK);
    }

}
