package com.xikra.staybooker.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder(access = AccessLevel.PUBLIC)
@Entity
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adress_id")
    private Adress adress;
    private String description;
    private Integer rating;
    @ManyToMany
    @JoinTable(
            name = "hotel_amenitie",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "amenitie_id")
    )
    @Singular
    @Setter(AccessLevel.NONE)
    private Set<Amenity> amenities = new HashSet<>();

    @OneToMany(mappedBy = "hotel")
    @Singular
    @Setter(AccessLevel.NONE)
    private Set<Room> rooms = new HashSet<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @Singular
    @Setter(AccessLevel.NONE)
    private Set<Image> images = new HashSet<>();

    public void addAmenitiy(Amenity amenity){
        amenities.add(amenity);
    }

    public void removeAmenity(Amenity amenity){
        amenities.remove(amenity);
    }

    public void addRoom(Room room){
        rooms.add(room);
    }

    public void removeRoom(Room room){
        rooms.remove(room);
        room.setHotel(null);
    }

    public void addImage(Image image){
        images.add(image);
    }

    public void removeImage(Image image){
        images.remove(image);
        image.setHotel(null);
    }
}
