package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Hotel;
import com.xikra.staybooker.model.HotelDTO;
import org.mapstruct.Mapper;

@Mapper
public interface HotelMapper {

    HotelDTO hotelToHotelDTO(Hotel hotel);

    Hotel hotelDTOToHotel(HotelDTO hotelDTO);
}
