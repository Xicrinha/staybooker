package com.xikra.staybooker.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class Image implements Serializable {

    public Image(Long id, Hotel hotel, String url) {
        this.id = id;
        this.setHotel(hotel);
        this.url = url;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    private String url;

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
        hotel.getImages().add(this);
    }
}
