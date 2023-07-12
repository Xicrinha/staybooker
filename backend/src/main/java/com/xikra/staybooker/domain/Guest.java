package com.xikra.staybooker.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Guest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstname;
    private String lastname;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adress_id")
    private Adress adress;
    private String telephoneNumber;
    private String email;
    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private Set<Reservation> reservations;
    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private Set<Review> reviews;
}
