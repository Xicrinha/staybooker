package com.xikra.staybooker.domain;

import com.xikra.staybooker.enums.PaymentMethod;
import com.xikra.staybooker.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private Set<Room> rooms;
    private LocalDate checkinDate;
    private LocalDate chackoutDate;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private BigDecimal totalPrice;
    private PaymentMethod paymentMethod;

}
