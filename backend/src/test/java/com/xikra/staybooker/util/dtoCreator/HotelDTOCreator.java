package com.xikra.staybooker.util.dtoCreator;

import com.xikra.staybooker.model.AmenityDTO;
import com.xikra.staybooker.model.HotelDTO;

import java.util.Set;

public class HotelDTOCreator {

    public static HotelDTO create_HotelDTO(){
        return HotelDTO.builder()
                .name("Hotel Plaza")
                .street("Rua das maravilhas milagrosas")
                .number("31 D")
                .city("Canareiros")
                .state("RJ")
                .zipcode("72830170")
                .description("O melhor Hotel da America latina")
                .rating(5)
                .amenities(Set.of(new AmenityDTO(1L,"wi-fi")))
                .build();
    }

    public static HotelDTO createdValidHotelDTO(){
        return HotelDTO.builder()
                .id(1L)
                .name("Hotel Plaza")
                .street("Rua das maravilhas milagrosas")
                .number("31 D")
                .city("Canareiros")
                .state("RJ")
                .zipcode("72830170")
                .description("O melhor Hotel da America latina")
                .rating(5)
                .amenities(Set.of(new AmenityDTO(1L,"wi-fi")))
                .build();
    }
}
