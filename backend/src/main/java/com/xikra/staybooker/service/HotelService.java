package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Hotel;
import com.xikra.staybooker.exceptions.NotFoundException;
import com.xikra.staybooker.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public List<Hotel> gettAllHotel() {
        return hotelRepository.findAll();
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
