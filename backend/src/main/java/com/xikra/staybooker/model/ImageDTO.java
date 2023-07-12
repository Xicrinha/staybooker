package com.xikra.staybooker.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class ImageDTO {
    private Long id;
    @NotNull(message = "The hotel is required")
    private HotelDTO hotel;
    @NotBlank(message = "Image URL is required")
    @Pattern(regexp = "^https?://.*$", message = "Image URL must start with 'http://' or 'https://'")
    private String url;
}
