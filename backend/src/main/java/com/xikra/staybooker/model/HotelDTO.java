package com.xikra.staybooker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class HotelDTO {

    private Long id;

    @NotBlank(message = "The name is required")
    private String name;

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

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "The description must be a maximum of 255 characters")
    private String description;

    @Max(value = 5, message = "The rating must be a maximum of 5")
    @Positive(message = "the rating must be positive value")
    private Integer rating;

    @NotEmpty(message = "Must select at least one amenity")
    private Set<AmenityDTO> amenities = new HashSet<>();

    private Set<RoomDTO> rooms = new HashSet<>();
    private Set<ImageDTO> images = new HashSet<>();

    public void addAmenitiy(AmenityDTO amenity){
        amenities.add(amenity);
    }

    public void removeAmenity(AmenityDTO amenity){
        amenities.remove(amenity);
    }

    public void addRoom(RoomDTO room){
        rooms.add(room);
    }

    public void removeRoom(RoomDTO room){
        rooms.remove(room);
        room.setHotel(null);
    }

    public void addImage(ImageDTO image){
        images.add(image);
    }

    public void removeImage(ImageDTO image){
        images.remove(image);
        image.setHotel(null);
    }
}
