package com.xikra.staybooker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xikra.staybooker.domain.Amenity;
import com.xikra.staybooker.mapper.AmenityMapper;
import com.xikra.staybooker.model.AmenityDTO;
import com.xikra.staybooker.service.AmenityService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AmenityController.class)
class AmenityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AmenityMapper amenityMapper;

    @MockBean
    private AmenityService amenityService;

    @Test
    @DisplayName("getAllAmenities returns list of amenity inside page object when successful")
    void getAllAmenities_ReturnsListAmenityInsidePageObject_WhenSuccessful() throws Exception {

        BDDMockito.when(amenityService.getAllAmenities()).thenReturn(List.of());

        AmenityDTO amenityDTO1 = AmenityDTO.builder().id(1L).name("wi-fi").build();
        AmenityDTO amenityDTO2 = AmenityDTO.builder().id(2L).name("piscina").build();
        AmenityDTO amenityDTO3 = AmenityDTO.builder().id(3L).name("tv a cabo").build();
        BDDMockito.when(amenityMapper.toDTOList(any())).thenReturn(List.of(amenityDTO1, amenityDTO2, amenityDTO3));

        mockMvc.perform(MockMvcRequestBuilders.get("/staybooker/amenities")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(3)));

    }

    @Test
    @DisplayName("getAmenityById returns amenity when successful")
    void getAmenityById_ReturnsAmenity_WhenSuccessful() throws Exception {

        AmenityDTO amenityDTO = AmenityDTO.builder().id(1L).name("wi-fi").build();

        Amenity amenity = Amenity.builder().id(1L).name("wi-fi").build();

        BDDMockito.when(amenityService.getAmenityById(1L)).thenReturn(Optional.of(amenity));
        BDDMockito.when(amenityMapper.toDTO(any(Amenity.class))).thenReturn(amenityDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/staybooker/amenities/{id}",1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".id").value(1));
    }

    @Test
    @DisplayName("getAmenityById Not found Amenity throws NotFoundException")
    void getAmenityById_NotFoundAddress_ThrowsNotFoundException() throws Exception {
        BDDMockito.when(amenityService.getAmenityById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/staybooker/amenities/{id}", 2l)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("save returns amenity when successful")
    void save_ReturnsAmenity_WhenSuccessful() throws Exception {
        AmenityDTO amenityDTOtoBeSaved = AmenityDTO.builder().name("wi-fi").build();
        Amenity amenityTobeSaved = Amenity.builder().name("wi-fi").build();
        Amenity amenityCreated = Amenity.builder().id(1L).name("wi-fi").build();
        AmenityDTO amenityDTOCreated = AmenityDTO.builder().id(1L).name("wi-fi").build();

        BDDMockito.when(amenityService.createAmenity(any(Amenity.class))).thenReturn(amenityCreated);
        BDDMockito.when(amenityMapper.toEntity(any(AmenityDTO.class))).thenReturn(amenityTobeSaved);
        BDDMockito.when(amenityMapper.toDTO(any(Amenity.class))).thenReturn(amenityDTOCreated);

        mockMvc.perform(post("/staybooker/amenities")
                .content(asJsonString(amenityDTOtoBeSaved))
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
    @DisplayName("save throwsBadRequestException when name is empty")
    void save_ThrowsBadRequestException_WhenNameIsEmpty() throws Exception {
        BDDMockito.when(amenityService.createAmenity(any(Amenity.class))).thenReturn(new Amenity());

        BDDMockito.when(amenityMapper.toDTO(any(Amenity.class))).thenReturn(new AmenityDTO());
        BDDMockito.when(amenityMapper.toEntity(any(AmenityDTO.class))).thenReturn(new Amenity());

        mockMvc.perform(post("/staybooker/amenities")
                .content(asJsonString(new AmenityDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("update returns amenity when successful")
    void update_ReturnAmenity_WhenSuccessful() throws Exception{
        String expectedNameUpdated = "Tv a cabo";

        Amenity amenityCreatedValidUpdated = Amenity.builder().id(1L).name("Tv a cabo").build();
        Amenity amenityCreated = Amenity.builder().id(1L).name("wi-fi").build();
        AmenityDTO amenityDTOCreatedValidUpdated = AmenityDTO.builder().id(1L).name("Tv a cabo").build();
        AmenityDTO amenityDTOToBeUpdated = AmenityDTO.builder().name("Tv a cabo").build();

        BDDMockito.when(amenityService.updateAmenity(any(Amenity.class),anyLong())).thenReturn(amenityCreatedValidUpdated);

        BDDMockito.when(amenityMapper.toEntity(any(AmenityDTO.class))).thenReturn(amenityCreated);
        BDDMockito.when(amenityMapper.toDTO(any(Amenity.class))).thenReturn(amenityDTOCreatedValidUpdated);

        mockMvc.perform(put("/staybooker/amenities/{id}", 1)
                .content(asJsonString(amenityDTOToBeUpdated))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expectedNameUpdated));
    }

    @Test
    @DisplayName("delete remove amenity when successful")
    void delete_RemovesAmenity_WhenSuccessful() throws Exception{
        BDDMockito.when(amenityService.deleteAmenity(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/staybooker/amenities/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}