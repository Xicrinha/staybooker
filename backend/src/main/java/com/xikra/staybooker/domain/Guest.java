package com.xikra.staybooker.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class Guest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstname;
    private String lastname;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adress_id")
    private Adress adress;
    private String phoneNumber;
    private String email;
    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    private Set<Reservation> reservations = new HashSet<>();
    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    private Set<Review> reviews = new HashSet<>();

    public void addReservation(Reservation reservation){
        reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation){
        reservations.remove(reservation);
        reservation.setGuest(null);
    }

    public void addReview(Review review){
        reviews.add(review);
    }

    public void removeReview(Review review){
        reviews.remove(review);
        review.setGuest(null);
    }
}
