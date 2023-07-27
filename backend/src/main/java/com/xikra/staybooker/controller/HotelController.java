package com.xikra.staybooker.controller;

import com.xikra.staybooker.domain.Hotel;
import com.xikra.staybooker.exceptions.NotFoundException;
import com.xikra.staybooker.mapper.HotelMapper;
import com.xikra.staybooker.model.HotelDTO;
import com.xikra.staybooker.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staybooker/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final HotelMapper hotelMapper;


    @PostMapping
    public ResponseEntity<HotelDTO> createHotel(@RequestBody @Valid HotelDTO hotelDTO){
        Hotel createdHotel = hotelService.createHotel(hotelMapper.toEntity(hotelDTO));
        return new ResponseEntity<>(hotelMapper.toDTO(createdHotel), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<HotelDTO>> gettAllHotel(){
        List<HotelDTO> hotelDTOList = hotelService.gettAllHotel()
                .stream()
                .map(hotelMapper::toDTO)
                .toList();
        return new ResponseEntity<>(hotelDTOList, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public HotelDTO getHotelById(@PathVariable("id") Long id){
        return hotelService.getHotelById(id).map(hotelMapper::toDTO)
                .orElseThrow(NotFoundException::new);
    }
}
