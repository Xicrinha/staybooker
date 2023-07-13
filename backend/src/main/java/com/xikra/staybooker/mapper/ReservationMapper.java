package com.xikra.staybooker.mapper;

import com.xikra.staybooker.domain.Reservation;
import com.xikra.staybooker.model.ReservationDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ReservationMapper {

    ReservationDTO reservationToReservationDTO(Reservation reservation);

    Reservation reservationDTOToReservation(ReservationDTO reservationDTO);
}
