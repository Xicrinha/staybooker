package com.xikra.staybooker.controller;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.mapper.AddressMapper;
import com.xikra.staybooker.mapper.AddressMapperImpl;
import com.xikra.staybooker.model.AddressDTO;
import com.xikra.staybooker.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AddressControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(AddressControllerTest.class);

    @Mock
    private AddressService addressService;
    private AddressMapper addressMapper = new AddressMapperImpl();
    private AddressController addressController;

    @BeforeEach
    void setUp() {
        addressController = new AddressController(addressService, addressMapper);
    }

    @Test
    void testCreateAddress_returnAddressDTO_WhenFieldsAreValid() {
        AddressDTO addressDTO = AddressDTO.builder()
                .street("Rua das maravilhas milagrosas")
                .number("35 C")
                .city("Canareiros")
                .state("RJ")
                .zipcode("72830170")
                .build();

        Address address = Address.builder()
                .id(1L)
                .street("Rua maravilhas milagrosas")
                .number("35 C")
                .city("Canareiros")
                .state("RJ")
                .zipcode("72830170")
                .build();

        when(addressService.createAddress(any(Address.class))).thenReturn(address);

        ResponseEntity<AddressDTO> response = addressController.createAddress(addressDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    /*

    @Test
    void getAllAddress() {
    }

    @Test
    void getAddressById() {
    }

    @Test
    void updateAddress() {
    }

    @Test
    void deleteAddress() {
    }

    @Test
    void addressPatch() {
    }

     */

}