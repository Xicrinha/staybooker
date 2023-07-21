package com.xikra.staybooker.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class AddressDTO {

    private Long id;
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
}
