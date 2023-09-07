package com.xikra.staybooker.util.entityCreator;

import com.xikra.staybooker.domain.Amenity;
import com.xikra.staybooker.domain.Hotel;
import com.xikra.staybooker.model.AmenityDTO;
import com.xikra.staybooker.model.HotelDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HotelCreator {

    public static Hotel create_Hotel(){
        Hotel hotel = Hotel.builder()
                .name("Hotel Plaza")
                .address(AddressCreator.createAddressToBeSaved())
                .description("O melhor Hotel da America latina")
                .rating(5)
                .build();
        return hotel;
    }

    public static Hotel createdValidHotel(){
        return Hotel.builder()
                .id(1L)
                .name("Hotel Plaza")
                .address(AddressCreator.createdValidAddress())
                .description("O melhor Hotel da America latina")
                .rating(5)
                .build();
    }

    public static List<Hotel> listCreatedValidHotel(){
        List<Hotel> hotelList = new ArrayList<>();

        for(int i = 0; i < 0; i++){
            hotelList.add(createdValidHotel());
        }

        return  hotelList;
    }
}
