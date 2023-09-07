package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Hotel;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface HotelService {

    public Hotel createHotel(Hotel hotel);

    public Page<Hotel> getAllHotel(Integer pageNumber, Integer pageSize);

    public Optional<Hotel> getHotelById(Long id);

    public Hotel updateHotel(Long id, Hotel hotel);

    public boolean deleteHotel(Long id);

    public Hotel patchHotel(Long id, Hotel hotel);

    public Optional<Hotel> findByAddressZipcode(String zipcode);
}
