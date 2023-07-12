package com.xikra.staybooker.model;

import com.xikra.staybooker.enums.Availability;
import com.xikra.staybooker.enums.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class RoomDTO {
    private Long id;
    @NotBlank(message = "Room number is required")
    private String roomNumber;
    @NotNull(message = "Room type is required")
    private RoomType roomType;
    @NotNull(message = "The availability is required")
    private Availability availability;
    @NotNull(message = "Room price is required")
    @Positive(message = "Room price must be positive value")
    private BigDecimal price;
    @NotNull(message = "The hotel is required")
    private HotelDTO hotel;
    private ReservationDTO reservation;
}
