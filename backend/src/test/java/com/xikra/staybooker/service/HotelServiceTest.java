package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.domain.Hotel;
import com.xikra.staybooker.repository.HotelRepository;
import com.xikra.staybooker.util.entityCreator.HotelCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
class HotelServiceTest {

    @Mock
    HotelRepository hotelRepository;

    @InjectMocks
    HotelServiceImpl hotelService;

    @Test
    @DisplayName("getAllHotels returns list of hotel inside page object when successful")
    void getAllHotels_ReturnsListOfHotelInsidePageObject_WhenSuccessful(){
        Hotel hotel1 = HotelCreator.createdValidHotel();
        Hotel hotel2 = HotelCreator.createdValidHotel();
        Hotel hotel3 = HotelCreator.createdValidHotel();
        hotel2.setId(2L);
        hotel3.setId(3L);

        PageRequest pageRequest = PageRequest.of(0, 25);
        Page<Hotel> hotelPage = new PageImpl<>(List.of(hotel1, hotel2, hotel3));

        when(hotelRepository.findAll(pageRequest)).thenReturn(hotelPage);

        Page<Hotel> response = hotelService.getAllHotel(0, 25);

        assertThat(response.getSize()).isEqualTo(3);
    }

    @Test
    @DisplayName("findById returns hotel whrn successful")
    void findById_ReturnsHotel_WhenSuccessful(){
        Long expectedId = HotelCreator.createdValidHotel().getId();

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(HotelCreator.createdValidHotel()));

        Optional<Hotel> response = hotelService.getHotelById(1L);

        assertThat(response).isNotNull();
        assertThat(response.get().getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save returns when successful")
    void save_returnsHotel_WhenSuccessful(){
        Hotel hotelToBeSaved = HotelCreator.create_Hotel();

        when(hotelRepository.save(any(Hotel.class))).thenReturn(HotelCreator.createdValidHotel());

        Hotel response = hotelService.createHotel(hotelToBeSaved);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("update returns hotel when successful")
    void update_returnHotel_WhenSuccessful(){
        Integer expectedRating = 3;
        Hotel hotelToBeUpdate = HotelCreator.create_Hotel();
        hotelToBeUpdate.setRating(expectedRating);

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(HotelCreator.createdValidHotel()));

        Hotel hotelUpdated = HotelCreator.createdValidHotel();
        hotelUpdated.setRating(expectedRating);

        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotelUpdated);

        Hotel response = hotelService.updateHotel(1L, hotelToBeUpdate);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getRating()).isEqualTo(expectedRating);
    }

    @Test
    @DisplayName("delete remove hotel when successful")
    void delete_RemoveHotel_WhenSuccessful(){
        doNothing().when(hotelRepository).deleteById(anyLong());

        assertThatCode(() -> hotelService.deleteHotel(1L))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("patch returns Hotel when successful")
    void patch_ReturnsHotel_WhenSuccessful(){
        Integer expectedRating = 3;
        Hotel hotelToBeUpdate = new Hotel();
        hotelToBeUpdate.setAddress(new Address());
        hotelToBeUpdate.setRating(expectedRating);

        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(HotelCreator.createdValidHotel()));

        Hotel hotelUpdated = HotelCreator.createdValidHotel();
        hotelUpdated.setRating(expectedRating);

        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotelUpdated);

        Hotel response = hotelService.updateHotel(1L, hotelToBeUpdate);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getRating()).isEqualTo(expectedRating);
    }
}