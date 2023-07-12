package com.xikra.staybooker.model;

import com.xikra.staybooker.annotations.checkInCheckOut.CheckInCheckOut;
import com.xikra.staybooker.enums.PaymentMethod;
import com.xikra.staybooker.enums.ReservationStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@CheckInCheckOut
public class ReservationDTO {
    private Long id;
    @NotNull(message = "The guest is required")
    private GuestDTO guest;
    @NotEmpty(message = "Must select at least one room")
    private Set<RoomDTO> rooms = new HashSet<>();
    @NotNull(message = "Check-in date is required")
    @Future(message = "The check-in date must be a date in the future")
    private LocalDate checkinDate;
    @NotNull(message = "Checkout date is required")
    @Future(message = "Checkout date must be a date in the future")
    private LocalDate checkoutDate;
    @NotNull(message = "Booking status is required")
    private ReservationStatus status;
    @NotNull(message = "Total price is required")
    @Positive(message = "Total price must be a positive value")
    private BigDecimal totalPrice;
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    public void addRoom(RoomDTO room){
        rooms.add(room);
    }

    public void removeRoom(RoomDTO room){
        rooms.remove(room);
        room.setReservation(null);
    }
}
