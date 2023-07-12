package com.xikra.staybooker.annotations.checkInCheckOut;

import com.xikra.staybooker.model.ReservationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckInCheckOutValidator implements ConstraintValidator<CheckInCheckOut, ReservationDTO> {
    @Override
    public boolean isValid(ReservationDTO reservation, ConstraintValidatorContext context) {
        if (reservation == null || reservation.getCheckinDate() == null || reservation.getCheckoutDate() == null) {
            return true;
        }

        return reservation.getCheckoutDate().isAfter(reservation.getCheckinDate());
    }
}