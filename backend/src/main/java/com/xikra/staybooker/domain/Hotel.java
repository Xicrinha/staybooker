package com.xikra.staybooker.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
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
    private Set<Amenitie> amenities;
    @OneToMany(mappedBy = "hotel")
    private Set<Room> rooms;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private Set<Image> images;
}
