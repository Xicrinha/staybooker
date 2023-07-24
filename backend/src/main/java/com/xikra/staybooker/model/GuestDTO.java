package com.xikra.staybooker.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class GuestDTO {

    private Long id;
    @NotBlank(message = "First name is required")
    private String firstname;
    @NotBlank(message = "Last name is required")
    private String lastname;
    @NotBlank(message = "Street name is required")
    private String street;
    @NotBlank(message = "Number is required")
    private String number;
    @NotBlank(message = "The city is required")
    private String city;
    @NotBlank(message = "The state is required")
    @Size(min = 2, max = 2, message = "State must be exactly 2 characters long")
    private String state;
    @NotBlank(message = "The Zip Code is required")
    @Size(min = 5, max = 10, message = "The Zip Code must be between 5 and 10 characters")
    private String zipcode;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}", message = "The phone number must be in the format (XX) XXXX-XXXX or (XX) XXXXX-XXXX")
    private String phoneNumber;
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be in a valid format")
    private String email;
    private Set<ReservationDTO> reservations = new HashSet<>();
    private Set<ReviewDTO> reviews = new HashSet<>();

    public void addReservation(ReservationDTO reservation){
        reservations.add(reservation);
    }

    public void removeReservation(ReservationDTO reservation){
        reservations.remove(reservation);
        reservation.setGuest(null);
    }

    public void addReview(ReviewDTO review){
        reviews.add(review);
    }

    public void removeReview(ReviewDTO review){
        reviews.remove(review);
        review.setGuest(null);
    }
}
