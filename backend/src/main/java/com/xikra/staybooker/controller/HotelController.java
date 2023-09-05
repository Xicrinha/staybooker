package com.xikra.staybooker.controller;

import com.xikra.staybooker.domain.Hotel;
import com.xikra.staybooker.exceptions.NotFoundException;
import com.xikra.staybooker.mapper.HotelMapper;
import com.xikra.staybooker.model.HotelDTO;
import com.xikra.staybooker.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<Page<HotelDTO>> gettAllHotel(@RequestParam(required = false) Integer pageNumber,
                                                       @RequestParam(required = false) Integer pageSize){
        Page<HotelDTO> hotelDTOPage = hotelMapper.toDTOPage(hotelService.getAllHotel(pageNumber, pageSize));
        return new ResponseEntity<>(hotelDTOPage, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable("id") Long id){
        HotelDTO hotelDTO = hotelService.getHotelById(id).map(hotelMapper::toDTO)
                    .orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(hotelDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable("id") Long id, @RequestBody @Valid HotelDTO hotelDTO){
        Hotel updatedHotel = hotelService.updateHotel(id,hotelMapper.toEntity(hotelDTO));
        return new ResponseEntity<>(hotelMapper.toDTO(updatedHotel),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable("id") Long id){
        boolean deleted = hotelService.deleteHotel(id);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            throw new NotFoundException();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HotelDTO> patchHotel(@PathVariable Long id, @RequestBody HotelDTO hotelDTO){
        Hotel updatedHotel = hotelService.patchHotel(id,hotelMapper.toEntity(hotelDTO));
        return new ResponseEntity<>(hotelMapper.toDTO(updatedHotel),HttpStatus.OK);
    }

}
