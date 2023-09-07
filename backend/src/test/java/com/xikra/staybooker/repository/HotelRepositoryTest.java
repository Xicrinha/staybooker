package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Amenity;
import com.xikra.staybooker.domain.Hotel;
import com.xikra.staybooker.util.entityCreator.HotelCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DataJpaTest
class HotelRepositoryTest {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private AmenityRepository amenityRepository;

    @Test
    @DisplayName("test save method")
    void test_save(){
        Amenity amenity = amenityRepository.save(new Amenity(1L, "Piscina"));

        Hotel hotelToBeSaved = HotelCreator.create_Hotel();
        hotelToBeSaved.addAmenitiy(amenity);

        Hotel savedHotel = hotelRepository.save(hotelToBeSaved);

        assertThat(savedHotel).isNotNull();
        assertThat(savedHotel.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("test findById")
    void test_findById(){
        Amenity amenity = amenityRepository.save(new Amenity(1L, "Piscina"));

        Hotel hotelToBeSaved = HotelCreator.create_Hotel();
        hotelToBeSaved.addAmenitiy(amenity);
        Hotel hotelSaved = hotelRepository.save(hotelToBeSaved);

        Optional<Hotel> response = hotelRepository.findById(hotelSaved.getId());

        assertThat(response).isNotNull();
        assertThat(response.get().getId()).isEqualTo(hotelSaved.getId());
    }

    @Test
    @DisplayName("test findAll method")
    void test_findAll(){
        for(int i = 0; i<5; i++){
            hotelRepository.save(HotelCreator.create_Hotel());
        }

        PageRequest pageRequest = PageRequest.of(0, 25);
        Page<Hotel> response = hotelRepository.findAll(pageRequest);

        assertThat(response.getContent()).isNotNull();
        assertThat(response.getContent().size()).isEqualTo(5);
    }

    @Test
    @DisplayName("test deleteById method")
    void test_deleteById(){
        Hotel hotelToBeSaved = HotelCreator.create_Hotel();

        hotelRepository.save(hotelToBeSaved);

        assertThatCode(() -> hotelRepository.deleteById(1L)).doesNotThrowAnyException();
    }
}