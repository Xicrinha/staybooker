package com.xikra.staybooker.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class AmenityDTO {
    private Long id;
    @NotBlank(message = "The Name is required")
    private String name;
}
