package com.xikra.staybooker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.mapper.AddressMapper;
import com.xikra.staybooker.mapper.AddressMapperImpl;
import com.xikra.staybooker.model.AddressDTO;
import com.xikra.staybooker.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AddressControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @MockBean
    private AddressMapper addressMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private AddressDTO addressDTO;

    @BeforeEach
    void init() {
        addressDTO = AddressDTO.builder()
                .street("Rua das maravilhas milagrosas")
                .number("35 C")
                .city("Canareiros")
                .state("RJ")
                .zipcode("72830170")
                .build();
    }

    @Test
    public void testCreateAddress_ReturnsCreatedStatus() throws Exception {

        given(addressService.createAddress(any())).willAnswer(invocation -> invocation.getArgument(0));
        given(addressMapper.toEntity(any(AddressDTO.class))).willReturn(new Address());
        given(addressMapper.toDTO(any(Address.class))).willReturn(addressDTO);

        ResultActions response = mockMvc.perform(post("/staybooker/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addressDTO)));

        response.andExpect(status().isCreated());
    }
}