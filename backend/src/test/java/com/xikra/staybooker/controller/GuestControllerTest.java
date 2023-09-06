package com.xikra.staybooker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xikra.staybooker.domain.Guest;
import com.xikra.staybooker.mapper.GuestMapper;
import com.xikra.staybooker.model.GuestDTO;
import com.xikra.staybooker.service.GuestService;
import com.xikra.staybooker.util.dtoCreator.GuestDTOCreator;
import com.xikra.staybooker.util.entityCreator.GuestCreator;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GuestController.class)
class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GuestMapper guestMapper;

    @MockBean
    private GuestService guestService;

    @Test
    @DisplayName("getAllGuests returns list of guest inside page object when successful")
    void getAllGuests_ReturnsListOfGuestInsidePageObject_WhenSuccessful() throws Exception {
        BDDMockito.when(guestService.getAllGuests(any(), any())).thenReturn(new PageImpl<>(List.of()));

        GuestDTO guestDTO1 = GuestDTOCreator.createdValidGuestDTO();
        GuestDTO guestDTO2 = GuestDTOCreator.createdValidGuestDTO();
        GuestDTO guestDTO3 = GuestDTOCreator.createdValidGuestDTO();

        BDDMockito.when(guestMapper.toDTOPage(any(Page.class))).thenReturn(new PageImpl<>(List.of(guestDTO1, guestDTO2, guestDTO3)));

        mockMvc.perform(get("/staybooker/guests")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()", CoreMatchers.is(3)));
    }

    @Test
    @DisplayName("findById returns guest when successful")
    void findById_ReturnsGuest_WhenSuccessful() throws Exception {

        BDDMockito.when(guestService.getGuestById(1L)).thenReturn(Optional.of(GuestCreator.createdValidGuest()));
        BDDMockito.when(guestMapper.toDTO(any())).thenReturn(GuestDTOCreator.createdValidGuestDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/staybooker/guests/{id}", 1l)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".id").value(1));
    }

    @Test
    @DisplayName("findById Not found Guest throws NotFoundException")
    void findById_NotFoundGuest_ThrowsNotFoundException() throws Exception {
        BDDMockito.when(guestService.getGuestById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/staybooker/guests/{id}", 1l)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("save returns guest when successful")
    void save_ReturnsGuest_WhenSuccessful() throws Exception {
        BDDMockito.when(guestService.createGuest(any(Guest.class))).thenReturn(GuestCreator.createdValidGuest());

        BDDMockito.when(guestMapper.toEntity(any(GuestDTO.class))).thenReturn(GuestCreator.createGuestToBeSaved());
        BDDMockito.when(guestMapper.toDTO(any(Guest.class))).thenReturn(GuestDTOCreator.createdValidGuestDTO());

        mockMvc.perform(post("/staybooker/guests")
                .content(asJsonString(GuestDTOCreator.create_GuestDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    private static String asJsonString(final Object obj) {
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("save throws BadRequestException when all fields are empty")
    void save_ThrowBadRequestException_WhenAllFieldsAreEmpty() throws Exception {
        BDDMockito.when(guestService.createGuest(any(Guest.class))).thenReturn(new Guest());

        BDDMockito.when(guestMapper.toDTO(any(Guest.class))).thenReturn(new GuestDTO());
        BDDMockito.when(guestMapper.toEntity(any(GuestDTO.class))).thenReturn(new Guest());

        mockMvc.perform(post("/staybooker/guests")
                .content(asJsonString(new GuestDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("update returns guest when successful")
    void update_ReturnsGuest_WhenSuccessful() throws Exception {
        String expectedNumberUpdated = "(61) 99356-8712";

        GuestDTO put_GuestDTO = GuestDTOCreator.create_GuestDTO();
        put_GuestDTO.setPhoneNumber(expectedNumberUpdated);

        GuestDTO createdValidUpdatedGuestDTO = GuestDTOCreator.createdValidGuestDTO();
        createdValidUpdatedGuestDTO.setPhoneNumber(expectedNumberUpdated);

        BDDMockito.when(guestService.updateGuest(any(Guest.class), anyLong())).thenReturn(new Guest());

        BDDMockito.when(guestMapper.toDTO(any(Guest.class))).thenReturn(createdValidUpdatedGuestDTO);
        BDDMockito.when(guestMapper.toEntity(any(GuestDTO.class))).thenReturn(new Guest());

        mockMvc.perform(put("/staybooker/guests/{id}", 1)
                .content(asJsonString(put_GuestDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value(expectedNumberUpdated));
    }

    @Test
    @DisplayName("delete remove guest when successful")
    void delete_RemovesGuest_WhenSuccessful() throws Exception {
        BDDMockito.when(guestService.deleteGuest(1L)).thenReturn(true);

        mockMvc.perform(delete("/staybooker/guests/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("patch return guest when successful")
    void patch_ReturnGuest_WhenSuccessful() throws Exception {
        String expectedNumberUpdated = "(61) 99356-8712";

        GuestDTO patch_GuestDTO = new GuestDTO();
        patch_GuestDTO.setPhoneNumber(expectedNumberUpdated);

        GuestDTO createdValidUpdatedGuestDTO = GuestDTOCreator.createdValidGuestDTO();
        createdValidUpdatedGuestDTO.setPhoneNumber(expectedNumberUpdated);

        BDDMockito.when(guestService.guestPatch(anyLong(), any(Guest.class))).thenReturn(new Guest());

        BDDMockito.when(guestMapper.toDTO(any(Guest.class))).thenReturn(createdValidUpdatedGuestDTO);
        BDDMockito.when(guestMapper.toEntity(any(GuestDTO.class))).thenReturn(new Guest());

        mockMvc.perform(patch("/staybooker/guests/{id}", 1)
                .content(asJsonString(patch_GuestDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value(expectedNumberUpdated));
    }


}