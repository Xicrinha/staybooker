package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Amenity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AmenityRepositoryTest {

    @Autowired
    private AmenityRepository amenityRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("test save method")
    void test_save(){
        Amenity amenityToBeSaved = Amenity.builder().name("wi-fi").build();

        Amenity amenitySaved = amenityRepository.save(amenityToBeSaved);

        assertThat(amenitySaved).isNotNull();
        assertThat(amenitySaved.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("test findById method")
    void test_findById() {
        Amenity amenityToBeSaved = Amenity.builder().name("wi-fi").build();
        Amenity savedAmenity = testEntityManager.persistAndFlush(amenityToBeSaved);

        Optional<Amenity> response = amenityRepository.findById(savedAmenity.getId());

        assertThat(response).isNotNull();
        assertThat(response.get().getId()).isEqualTo(savedAmenity.getId());
    }

    @Test
    @DisplayName("test findAll method")
    void test_findAll(){
        Amenity amenity1 = Amenity.builder().name("wi-fi").build();
        Amenity amenity2 = Amenity.builder().name("piscina").build();
        Amenity amenity3 = Amenity.builder().name("Tv a Cabo").build();

        amenityRepository.saveAll(List.of(amenity1, amenity2, amenity3));

        List<Amenity> response = amenityRepository.findAll();

        assertThat(response).isNotNull();
        System.out.println(response.get(0).getName());
        assertThat(response.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("test deleteById method")
    void test_deleteById(){
        Amenity amenityToBeSaved = Amenity.builder().name("wi-fi").build();

        amenityRepository.save(amenityToBeSaved);

        assertThatCode(() -> amenityRepository.deleteById(1L)).doesNotThrowAnyException();
    }


}