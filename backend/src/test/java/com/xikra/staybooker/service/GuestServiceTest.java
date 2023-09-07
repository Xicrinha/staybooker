package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.domain.Guest;
import com.xikra.staybooker.model.GuestDTO;
import com.xikra.staybooker.repository.GuestRepository;
import com.xikra.staybooker.util.entityCreator.GuestCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class GuestServiceTest {

    @Mock
    GuestRepository guestRepository;

    @InjectMocks
    GuestServiceImpl guestService;

    @Test
    @DisplayName("getAllGuests returns list of guest inside page object when successful")
    void getAllGuests_ReturnsListOfGuestInsidePageObject_WhenSuccessful(){
        PageRequest pageRequest = PageRequest.of(0, 25);
        Page<Guest> guestPage = new PageImpl<>(GuestCreator.listCreatedValidGuest());

        when(guestRepository.findAll(pageRequest)).thenReturn(guestPage);

        Page<Guest> response = guestService.getAllGuests(0, 25);

        assertThat(response.getSize()).isEqualTo(5);
    }

    @Test
    @DisplayName("findById returns guest when successful")
    void findById_ReturnsGuest_WhenSuccessful(){
        Long expectedId = GuestCreator.createdValidGuest().getId();

        when(guestRepository.findById(1L)).thenReturn(Optional.of(GuestCreator.createdValidGuest()));

        Optional<Guest> response = guestService.getGuestById(1L);

        assertThat(response).isNotNull();
        assertThat(response.get().getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save returns guest when successful")
    void save_returnsGuest_WhenSuccessful(){
        Guest guestToBeSaved = GuestCreator.createGuestToBeSaved();

        when(guestRepository.save(any(Guest.class))).thenReturn(GuestCreator.createdValidGuest());

        Guest response = guestService.createGuest(guestToBeSaved);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("update returns guest when successful")
    void update_returnsGuest_WhenSuccessful(){
        String expectedPhoneNumber = "(61) 99356-8712";
        Guest guestToBeUpdate = GuestCreator.createGuestToBeSaved();
        guestToBeUpdate.setPhoneNumber(expectedPhoneNumber);

        when(guestRepository.findById(anyLong())).thenReturn(Optional.of(GuestCreator.createdValidGuest()));

        Guest guestUpdated = GuestCreator.createdValidGuest();
        guestUpdated.setPhoneNumber(expectedPhoneNumber);

        when(guestRepository.save(any(Guest.class))).thenReturn(guestUpdated);

        Guest response = guestService.updateGuest(guestToBeUpdate, 1L);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(1L);
        Assertions.assertThat(response.getPhoneNumber()).isEqualTo(expectedPhoneNumber);
    }

    @Test
    @DisplayName("delete remove guest when successful")
    void delete_RemoveGuest_WhenSuccessful(){
        doNothing().when(guestRepository).deleteById(1L);

        assertThatCode(() -> guestService.deleteGuest(1L))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("patch returns guest When Successful")
    void patch_ReturnsGuest_WhenSuccessful(){
        String expectedPhoneNumber = "(61) 99356-8712";
        Guest guestToBeUpdate = new Guest();
        guestToBeUpdate.setAddress(new Address());
        guestToBeUpdate.setPhoneNumber(expectedPhoneNumber);

        when(guestRepository.findById(anyLong())).thenReturn(Optional.of(GuestCreator.createdValidGuest()));

        Guest guestUpdated = GuestCreator.createdValidGuest();
        guestUpdated.setPhoneNumber(expectedPhoneNumber);

        when(guestRepository.save(any(Guest.class))).thenReturn(guestUpdated);

        Guest response = guestService.guestPatch(1L,guestToBeUpdate);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId()).isEqualTo(1L);
        Assertions.assertThat(response.getPhoneNumber()).isEqualTo(expectedPhoneNumber);
    }

    @Test
    @DisplayName("findByZipcode returns guest when successful")
    void findByZipcode_ReturnsGuest_WhenSuccessful(){
        String expectedZipcode = GuestCreator.createdValidGuest().getAddress().getZipcode();

        when(guestRepository.findByAddressZipcode(anyString())).thenReturn(Optional.of(GuestCreator.createdValidGuest()));

        Optional<Guest> response = guestService.findByAddressZipcode(expectedZipcode);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.get().getAddress().getZipcode()).isEqualTo(expectedZipcode);
    }
}