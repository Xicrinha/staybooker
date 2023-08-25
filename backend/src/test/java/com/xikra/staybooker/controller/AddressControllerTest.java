package com.xikra.staybooker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.mapper.AddressMapper;
import com.xikra.staybooker.model.AddressDTO;
import com.xikra.staybooker.service.AddressService;
import com.xikra.staybooker.util.entityCreator.AddressCreator;
import com.xikra.staybooker.util.dtoCreator.AddressDTOCreator;
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
@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressMapper addressMapperMock;

    @MockBean
    private AddressService addressServiceMock;

    @Test
    @DisplayName("getAllAddress return list of address inside page object when successful")
    void getAllAddress_ReturnsListOfAddressInsidePageObject_WhenSuccessful() throws Exception {

        Page<Address> addressPage = new PageImpl<>(List.of(AddressCreator.createdValidAddress()));
        BDDMockito.when(addressServiceMock.getAllAddress(any(), any(), any(), any())).thenReturn(addressPage);

        Page<AddressDTO> addressDTOPage = new PageImpl<>(List.of(AddressDTOCreator.createdValidAddressDTO()));
        BDDMockito.when(addressMapperMock.toDTOPage(any(Page.class))).thenReturn(addressDTOPage);


        mockMvc.perform(get("/staybooker/addresses")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()", CoreMatchers.is(1)));
    }

    @Test
    @DisplayName("getAddressByZipcode returns address by zipcode when successful")
    void getAddressByZipcode_ReturnsAddressByZipcode_WhenSuccessfull() throws Exception {
        String expectedZipcode = AddressCreator.createdValidAddress().getZipcode();

        BDDMockito.when(addressServiceMock.getAddressByZipcode("72830170")).thenReturn(Optional.of(AddressCreator.createdValidAddress()));
        BDDMockito.when(addressMapperMock.toDTO(any())).thenReturn(AddressDTOCreator.createdValidAddressDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/staybooker/addresses/zipcode")
                .param("zipcode", expectedZipcode)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".zipcode").value(expectedZipcode));
    }

    @Test
    @DisplayName("Find by id returns address when successful")
    void findById_ReturnsAddress_WhenSuccessful() throws Exception {
        Long expectedId = AddressDTOCreator.createdValidAddressDTO().getId();

        BDDMockito.when(addressServiceMock.getAddresById(1L)).thenReturn(Optional.ofNullable(AddressCreator.createdValidAddress()));
        BDDMockito.when(addressMapperMock.toDTO(any())).thenReturn(AddressDTOCreator.createdValidAddressDTO());

        mockMvc.perform(MockMvcRequestBuilders.get("/staybooker/addresses/{id}", 1l)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath(".id").value(1));
    }


    @Test
    @DisplayName("Find by id Not Found Address throws NotFoundException")
    void findById_NotFoundAddress_ThrowsNotFoundException() throws Exception {
        BDDMockito.when(addressServiceMock.getAddresById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/staybooker/addresses/{id}", 2l)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }


    @Test
    @DisplayName("save returns address when successful")
    void save_ReturnsAddress_WhenSuccessful() throws Exception {

        BDDMockito.when(addressServiceMock.createAddress(any(Address.class))).thenReturn(AddressCreator.createdValidAddress());

        BDDMockito.when(addressMapperMock.toEntity(any(AddressDTO.class))).thenReturn(AddressCreator.createAddressToBeSaved());
        BDDMockito.when(addressMapperMock.toDTO(any(Address.class))).thenReturn(AddressDTOCreator.createdValidAddressDTO());

        mockMvc.perform(post("/staybooker/addresses")
                .content(asJsonString(AddressDTOCreator.post_AddressDTO()))
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
    @DisplayName("save throws Bad Request Exception When All fields is empty")
    void save_ThrowsBadRequestException_WhenStreetIsEmpty() throws Exception {
        BDDMockito.when(addressServiceMock.createAddress(any(Address.class))).thenReturn(new Address());

        BDDMockito.when(addressMapperMock.toEntity(any(AddressDTO.class))).thenReturn(new Address());
        BDDMockito.when(addressMapperMock.toDTO(any(Address.class))).thenReturn(new AddressDTO());

        mockMvc.perform(post("/staybooker/addresses")
                        .content(asJsonString(new AddressDTO()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



    @Test
    @DisplayName("update returns address when successful")
    void update_ReturnAddress_WhenSuccessful() throws Exception {
        String expectedStreetUpdated = AddressDTOCreator.put_AddressDTO().getStreet();

        BDDMockito.when(addressServiceMock.updateAddress(anyLong(), any(Address.class))).thenReturn(AddressCreator.createdValidUpdatedAddress());

        BDDMockito.when(addressMapperMock.toEntity(any(AddressDTO.class))).thenReturn(AddressCreator.createdValidAddress());
        BDDMockito.when(addressMapperMock.toDTO(any(Address.class))).thenReturn(AddressDTOCreator.createdValidUpdatedAddressDTO());

        mockMvc.perform(put("/staybooker/addresses/{id}", 1)
                .content(asJsonString(AddressDTOCreator.put_AddressDTO()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value(expectedStreetUpdated));
    }
    @Test
    @DisplayName("delete remove address when successful")
    void delete_RemovesAddress_WhenSuccessful() throws Exception {

        BDDMockito.when(addressServiceMock.deleteAddress(1L)).thenReturn(true);

        mockMvc.perform(delete("/staybooker/addresses/{id}", 1))
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("patch returns address when successful")
    void patch_ReturnAddress_WhenSuccessful() throws Exception {
        String expectedStreetUpdated = AddressDTOCreator.put_AddressDTO().getStreet();

        BDDMockito.when(addressServiceMock.addressPatch(anyLong(),any(Address.class))).thenReturn(AddressCreator.createdValidUpdatedAddress());

        BDDMockito.when(addressMapperMock.toEntity(any(AddressDTO.class))).thenReturn(AddressCreator.createdValidAddress());
        BDDMockito.when(addressMapperMock.toDTO(any(Address.class))).thenReturn(AddressDTOCreator.createdValidUpdatedAddressDTO());

        mockMvc.perform(patch("/staybooker/addresses/{id}", 1)
                        .content(asJsonString(AddressDTOCreator.patch_AddressDTO()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.street").value(expectedStreetUpdated));
    }

}