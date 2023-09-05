package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Hotel;
import com.xikra.staybooker.exceptions.NotFoundException;
import com.xikra.staybooker.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;
    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Page<Hotel> getAllHotel(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        return hotelRepository.findAll(pageRequest);
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

    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    public Hotel updateHotel(Long id, Hotel hotel) {
        Hotel existingHotel = hotelRepository.findById(id).orElseThrow(NotFoundException::new);
        existingHotel.setName(hotel.getName());
        existingHotel.getAddress().setStreet(hotel.getAddress().getStreet());
        existingHotel.getAddress().setNumber(hotel.getAddress().getNumber());
        existingHotel.getAddress().setCity(hotel.getAddress().getCity());
        existingHotel.getAddress().setState(hotel.getAddress().getState());
        existingHotel.getAddress().setZipcode(hotel.getAddress().getZipcode());
        existingHotel.setDescription(hotel.getDescription());
        existingHotel.setRating(hotel.getRating());

        return hotelRepository.save(existingHotel);

    }

    public boolean deleteHotel(Long id) {
        if(hotelRepository.existsById(id)){
            hotelRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Hotel patchHotel(Long id, Hotel hotel) {
        Hotel existingHotel = hotelRepository.findById(id).orElseThrow(NotFoundException::new);
        if(StringUtils.hasText(hotel.getName())){
            existingHotel.setName(hotel.getName());
        }
        if(StringUtils.hasText(hotel.getAddress().getStreet())){
            existingHotel.getAddress().setStreet(hotel.getAddress().getStreet());
        }
        if(StringUtils.hasText(hotel.getAddress().getNumber())){
            existingHotel.getAddress().setNumber(hotel.getAddress().getNumber());
        }
        if(StringUtils.hasText(hotel.getAddress().getCity())){
            existingHotel.getAddress().setCity(hotel.getAddress().getCity());
        }
        if(StringUtils.hasText(hotel.getAddress().getState())){
            existingHotel.getAddress().setState(hotel.getAddress().getState());
        }
        if(StringUtils.hasText(hotel.getAddress().getZipcode())){
            existingHotel.getAddress().setZipcode(hotel.getAddress().getZipcode());
        }
        if(StringUtils.hasText(hotel.getDescription())){
            existingHotel.setDescription(hotel.getDescription());
        }
        if(hotel.getRating() != null){
            existingHotel.setRating(hotel.getRating());
        }

        return hotelRepository.save(existingHotel);
    }
}
