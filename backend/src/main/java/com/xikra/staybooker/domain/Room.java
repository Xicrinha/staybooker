package com.xikra.staybooker.domain;

import com.xikra.staybooker.enums.Availability;
import com.xikra.staybooker.enums.RoomType;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

public class Room implements Serializable {

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
}
