package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Guest;
import com.xikra.staybooker.util.entityCreator.GuestCreator;
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
class GuestRepositoryTest {

    @Autowired
    private GuestRepository guestRepository;

    @Test
    @DisplayName("test save method")
    void test_save(){
        Guest guestToBeSaved = GuestCreator.createGuestToBeSaved();

        Guest savedGuest = guestRepository.save(guestToBeSaved);

        assertThat(savedGuest).isNotNull();
        assertThat(savedGuest.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("test findById method")
    void test_findById(){
        Guest guestToBeSaved = GuestCreator.createGuestToBeSaved();
        Guest savedGuest = guestRepository.save(guestToBeSaved);

        Optional<Guest> response = guestRepository.findById(savedGuest.getId());

        assertThat(response).isPresent();
        assertThat(response.get()).isNotNull();
    }

    @Test
    @DisplayName("test findAll method")
    void test_findAll(){
        for(int i =0; i<5; i++){
            guestRepository.save(GuestCreator.createGuestToBeSaved());
        }

        PageRequest pageRequest = PageRequest.of(0, 25);
        Page<Guest> response = guestRepository.findAll(pageRequest);

        assertThat(response.getContent()).isNotNull();
        assertThat(response.getContent().size()).isEqualTo(5);
    }

    @Test
    @DisplayName("test deleteById method")
    void test_deleteById(){
        Guest guestToBeSaved = GuestCreator.createGuestToBeSaved();

        guestRepository.save(guestToBeSaved);

        assertThatCode(() -> guestRepository.deleteById(1L)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("test findByAddressZipcode method")
    void test_findByAddressZipcode(){
        Guest guestToBeSaved = GuestCreator.createGuestToBeSaved();
        Guest savedGuest = guestRepository.save(guestToBeSaved);

        Optional<Guest> response = guestRepository.findByAddressZipcode(savedGuest.getAddress().getZipcode());

        assertThat(response).isPresent();
        assertThat(response.get()).isNotNull();
        assertThat(response.get().getAddress().getZipcode()).isEqualTo(savedGuest.getAddress().getZipcode());
    }

}