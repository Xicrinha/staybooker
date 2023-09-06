package com.xikra.staybooker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xikra.staybooker.domain.Hotel;
import com.xikra.staybooker.mapper.HotelMapper;
import com.xikra.staybooker.model.HotelDTO;
import com.xikra.staybooker.service.HotelService;
import com.xikra.staybooker.util.dtoCreator.HotelDTOCreator;
import com.xikra.staybooker.util.entityCreator.HotelCreator;
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
@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelMapper hotelMapper;

    @MockBean
    private HotelService hotelService;

    @Test
    @DisplayName("getAllHotels returns list of hotel inside page Object when successdful")
    void getAllHotels_ReturnsListOfHotelInsidePageObject_WhenSuccessful() throws Exception {
        BDDMockito.when(hotelService.getAllHotel(any(), any())).thenReturn(new PageImpl<>(List.of()));

        HotelDTO hotelDTO1 = HotelDTOCreator.createdValidHotelDTO();
        HotelDTO hotelDTO2 = HotelDTOCreator.createdValidHotelDTO();
        HotelDTO hotelDTO3 = HotelDTOCreator.createdValidHotelDTO();

        BDDMockito.when(hotelMapper.toDTOPage(any(Page.class))).thenReturn(new PageImpl(List.of(hotelDTO1, hotelDTO2, hotelDTO3)));

        mockMvc.perform(get("/staybooker/hotels")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()", CoreMatchers.is(3)));
    }

    @Test
    @DisplayName("findById  returns hotel when successful")
    void findById_ReturnsHotel_WhenSuccessful() throws Exception {

        BDDMockito.when(hotelService.getHotelById(1L)).thenReturn(Optional.of(HotelCreator.createdValidHotel()));
        BDDMockito.when(hotelMapper.toDTO(any())).thenReturn(HotelDTOCreator.createdValidHotelDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/staybooker/hotels/{id}", 1l)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("findById Not found hotel throws NotFoundException")
    void findById_NotFoundHotel_ThrowsNotFoundException() throws Exception {
        BDDMockito.when(hotelService.getHotelById(any())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/staybooker/hotels/{id}", 1l)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("save returns hotel when successful")
    void save_ReturnsHotel_WhenSuccesful() throws Exception {
        BDDMockito.when(hotelService.createHotel(any(Hotel.class))).thenReturn(HotelCreator.createdValidHotel());

        BDDMockito.when(hotelMapper.toEntity(any(HotelDTO.class))).thenReturn(HotelCreator.create_Hotel());
        BDDMockito.when(hotelMapper.toDTO(any(Hotel.class))).thenReturn(HotelDTOCreator.createdValidHotelDTO());

        mockMvc.perform(post("/staybooker/hotels")
                .content(asJsonString(HotelDTOCreator.create_HotelDTO()))
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
    @DisplayName("save throw BadRequestException when all fields are Empty")
    void save_ThrowBadRequestException_WhenAllFieldsAreEmpty() throws Exception {
        BDDMockito.when(hotelService.createHotel(any(Hotel.class))).thenReturn(new Hotel());

        BDDMockito.when(hotelMapper.toDTO(any(Hotel.class))).thenReturn(new HotelDTO());
        BDDMockito.when(hotelMapper.toEntity(any(HotelDTO.class))).thenReturn(new Hotel());

        mockMvc.perform(post("/staybooker/hotels")
                .content(asJsonString(new HotelDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("update returns hotel when successful")
    void update_ReturnsHotel_WhenSuccessful() throws Exception {
        Integer expectedRatingUpdated = 3;

        HotelDTO put_HotelDTO = HotelDTOCreator.create_HotelDTO();
        put_HotelDTO.setRating(expectedRatingUpdated);

        HotelDTO createdValidUpdatedHotelDTO = HotelDTOCreator.createdValidHotelDTO();
        createdValidUpdatedHotelDTO.setRating(expectedRatingUpdated);

        BDDMockito.when(hotelService.updateHotel(anyLong(), any(Hotel.class))).thenReturn(new Hotel());

        BDDMockito.when(hotelMapper.toEntity(any(HotelDTO.class))).thenReturn(new Hotel());
        BDDMockito.when(hotelMapper.toDTO(any(Hotel.class))).thenReturn(createdValidUpdatedHotelDTO);

        mockMvc.perform(put("/staybooker/hotels/{id}", 1)
                .content(asJsonString(put_HotelDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(expectedRatingUpdated));
    }

    @Test
    @DisplayName("delete remove hotel when successful")
    void delete_RemoveHotel_WhenSuccessful() throws Exception {
        BDDMockito.when(hotelService.deleteHotel(any())).thenReturn(true);

        mockMvc.perform(delete("/staybooker/hotels/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("patch returns hotel when successful")
    void patch_ReturnsHotel_WhenSuccessful() throws Exception {
        Integer expectedRatingUpdated = 3;

        HotelDTO patch_HotelDTO = new HotelDTO();
        patch_HotelDTO.setRating(expectedRatingUpdated);

        HotelDTO createdValidUpdatedHotelDTO = HotelDTOCreator.createdValidHotelDTO();
        createdValidUpdatedHotelDTO.setRating(expectedRatingUpdated);

        BDDMockito.when(hotelService.patchHotel(anyLong(), any(Hotel.class))).thenReturn(new Hotel());

        BDDMockito.when(hotelMapper.toEntity(any(HotelDTO.class))).thenReturn(new Hotel());
        BDDMockito.when(hotelMapper.toDTO(any(Hotel.class))).thenReturn(createdValidUpdatedHotelDTO);

        mockMvc.perform(patch("/staybooker/hotels/{id}", 1)
                .content(asJsonString(patch_HotelDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(expectedRatingUpdated));
    }
}