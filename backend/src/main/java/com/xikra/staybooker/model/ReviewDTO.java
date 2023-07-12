package com.xikra.staybooker.model;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class ReviewDTO {
    private Long id;
    @NotNull(message = "The guest is required")
    private GuestDTO guest;
    @NotNull(message = "The hotel is required")
    private HotelDTO hotel;
    @NotNull(message = "Rating is required")
    @Max(value = 5, message = "The maximum rating is 5")
    @Positive(message = "the rating must be positive value")
    private Integer rating;
    @NotBlank(message = "Comment is required")
    @Size(max = 355, message = "The comment must be a maximum of 355 characters.")
    private String comment;
}
