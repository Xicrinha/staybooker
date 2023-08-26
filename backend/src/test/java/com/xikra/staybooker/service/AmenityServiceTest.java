package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Amenity;
import com.xikra.staybooker.repository.AmenityRepository;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AmenityServiceTest {

    @Mock
    AmenityRepository amenityRepository;

    @InjectMocks
    AmenityServiceImpl amenityService;

    @Test
    @DisplayName("getAllAmenities returns list of amenity when successful")
    void getAllAmenities_ReturnListOfAmenity_WhenSuccessful(){
        Amenity amenity1 = Amenity.builder().id(1L).name("TV a cabo").build();
        Amenity amenity2 = Amenity.builder().id(2L).name("WI-FI").build();
        Amenity amenity3 = Amenity.builder().id(3L).name("piscina").build();

        List<Amenity> amenities = List.of(amenity1,amenity2,amenity3);

        when(amenityRepository.findAll()).thenReturn(amenities);

        List<Amenity> response = amenityService.getAllAmenities();

        assertThat(response.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("findById Returns Amenity when successful")
    void findById_ReturnsAmenity_WhenSuccessful(){
        Long expectedId = 1L;

        Amenity amenity = Amenity.builder().id(1L).name("Wi-FI").build();

        when(amenityRepository.findById(anyLong())).thenReturn(Optional.of(amenity));

        Optional<Amenity> response = amenityService.getAmenityById(1L);

        assertThat(response).isNotNull();
        assertThat(response.get().getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save returns amenity when successful")
    void save_ReturnsAddress_WhenSuccessful() {
        Amenity amenityToBeSaved = Amenity.builder().name("piscina").build();

        Amenity amenityCreated = Amenity.builder().id(1L).name("piscina").build();

        when(amenityRepository.save(any(Amenity.class))).thenReturn(amenityCreated);

        Amenity response = amenityService.createAmenity(amenityToBeSaved);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("update returns amenity when successful")
    void update_ReturnAmenity_WhenSuccessful(){
        Amenity amenityToBeUpdate = Amenity.builder().name("piscina").build();
        Amenity amenityCreated = Amenity.builder().id(1L).name("wi-fi").build();

        when(amenityRepository.findById(anyLong())).thenReturn(Optional.of(amenityCreated));

        Amenity amenityUpdated = Amenity.builder().id(1L).name("piscina").build();

        when(amenityRepository.save(any(Amenity.class))).thenReturn(amenityUpdated);

        Amenity response = amenityService.updateAmenity(amenityToBeUpdate, 1L);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("piscina");
    }

    @Test
    @DisplayName("delete remove address when successful")
    void delete_RemovesAddress_WhenSuccessful(){
        doNothing().when(amenityRepository).deleteById(1L);

        assertThatCode(()-> amenityService.deleteAmenity(1L)).doesNotThrowAnyException();
    }
}