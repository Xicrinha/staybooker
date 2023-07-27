package com.xikra.staybooker.domain;

import com.xikra.staybooker.enums.Availability;
import com.xikra.staybooker.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class Room implements Serializable {

    public Room(Long id, String roomNumber, RoomType roomType, Availability availability, BigDecimal price, Hotel hotel, Reservation reservation) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.availability = availability;
        this.price = price;
        this.setHotel(hotel);
        this.reservation = reservation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String roomNumber;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    @Enumerated(EnumType.STRING)
    private Availability availability;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
        hotel.getRooms().add(this);
    }
}
