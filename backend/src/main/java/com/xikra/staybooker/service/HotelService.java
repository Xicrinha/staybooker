package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Amenity;
import com.xikra.staybooker.domain.Hotel;
import com.xikra.staybooker.domain.Image;
import com.xikra.staybooker.domain.Room;
import com.xikra.staybooker.model.HotelDTO;
import com.xikra.staybooker.repository.AmenityRepository;
import com.xikra.staybooker.repository.HotelRepository;
import com.xikra.staybooker.repository.ImageRepository;
import com.xikra.staybooker.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
