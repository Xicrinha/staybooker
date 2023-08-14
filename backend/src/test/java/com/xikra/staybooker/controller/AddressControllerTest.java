package com.xikra.staybooker.controller;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.mapper.AddressMapper;
import com.xikra.staybooker.model.AddressDTO;
import com.xikra.staybooker.service.AddressService;
import com.xikra.staybooker.util.AddressCreator;
import com.xikra.staybooker.util.AddressDTOCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class AddressControllerTest {

    @InjectMocks
    private AddressController addressController;

    @Mock
    private AddressMapper addressMapperMock;

    @Mock
    private AddressService addressServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Address> addressPage = new PageImpl<>(List.of(AddressCreator.createdValidAddress()));
        BDDMockito.when(addressServiceMock.getAllAddress(any(),any(),any(),any())).thenReturn(addressPage);

        PageImpl<AddressDTO> addressDTOPage = new PageImpl<>(List.of(AddressDTOCreator.createdValidAddressDTO()));
        BDDMockito.when(addressMapperMock.toDTOPage(any())).thenReturn(addressDTOPage);

        BDDMockito.when(addressServiceMock.getAddresById(1L)).thenReturn(Optional.ofNullable(AddressCreator.createdValidAddress()));

        BDDMockito.when(addressServiceMock.createAddress(any(Address.class))).thenReturn(AddressCreator.createdValidAddress());

        BDDMockito.when(addressServiceMock.updateAddress(anyLong(), any(Address.class))).thenReturn(AddressCreator.createdValidUpdatedAddress());

        BDDMockito.when(addressServiceMock.deleteAddress(1L)).thenReturn(true);

        BDDMockito.when(addressServiceMock.addressPatch(anyLong(),any(Address.class))).thenReturn(AddressCreator.createdValidUpdatedAddress());
    }

    @Test
    @DisplayName("List return list of address inside page object when successful")
    void list_ReturnsListOfAddressInsidePageObject_WhenSuccessful(){
        String expectedStreet = AddressCreator.createdValidAddress().getStreet();

        ResponseEntity<Page<AddressDTO>> response = addressController.getAllAddress(null, null, null,null);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().toList()).isNotEmpty().hasSize(1);
        assertThat(response.getBody().toList().get(0).getStreet()).isEqualTo(expectedStreet);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Find by id returns address when successful")
    void findById_ReturnsAddress_WhenSuccessful(){
        Long expectedId = AddressDTOCreator.createdValidAddressDTO().getId();

        BDDMockito.when(addressMapperMock.toDTO(any())).thenReturn(AddressDTOCreator.createdValidAddressDTO());

        ResponseEntity<AddressDTO> response = addressController.getAddressById(1L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isEqualTo(expectedId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /*
    @Test
    @DisplayName("Find by id Not Found Address throws NotFoundException")
    void findById_NotFoundAddress_ThrowsNotFoundException(){
        Long expectedId = AddressDTOCreator.createdValidAddressDTO().getId();

        BDDMockito.when(addressServiceMock.getAddresById(1L)).thenReturn(null);

        assertThatException().isThrownBy((ThrowableAssert.ThrowingCallable) addressController.getAddressById(1L));
    }
     */


    @Test
    @DisplayName("save returns address when successful")
    void save_ReturnsAddress_WhenSuccessful(){

        BDDMockito.when(addressMapperMock.toEntity(any(AddressDTO.class))).thenReturn(AddressCreator.createAddressToBeSaved());
        BDDMockito.when(addressMapperMock.toDTO(any(Address.class))).thenReturn(AddressDTOCreator.createdValidAddressDTO());

        ResponseEntity<AddressDTO> response = addressController.createAddress(AddressDTOCreator.post_AddressDTO());

        assertThat(response.getBody()).isNotNull().isEqualTo(AddressDTOCreator.createdValidAddressDTO());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("update returns address when successful")
    void update_ReturnAddress_WhenSuccessful(){
        String expectedStreetUpdated = AddressDTOCreator.put_AddressDTO().getStreet();

        BDDMockito.when(addressMapperMock.toEntity(any(AddressDTO.class))).thenReturn(AddressCreator.createdValidAddress());
        BDDMockito.when(addressMapperMock.toDTO(any(Address.class))).thenReturn(AddressDTOCreator.createdValidUpdatedAddressDTO());

        assertThatCode(() -> addressController.updateAddress(1L , AddressDTOCreator.put_AddressDTO()))
                .doesNotThrowAnyException();

        ResponseEntity<AddressDTO> response = addressController.updateAddress(1L , AddressDTOCreator.put_AddressDTO());

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStreet()).isEqualTo(expectedStreetUpdated);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete remove address when successful")
    void delete_RemovesAddress_WhenSuccessful(){

        assertThatCode(() -> addressController.deleteAddress(1L ))
                .doesNotThrowAnyException();

        ResponseEntity<Void> response = addressController.deleteAddress(1L );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("patch returns address when successful")
    void patch_ReturnAddress_WhenSuccessful(){
        String expectedStreetUpdated = AddressDTOCreator.put_AddressDTO().getStreet();

        BDDMockito.when(addressMapperMock.toEntity(any(AddressDTO.class))).thenReturn(AddressCreator.createdValidAddress());
        BDDMockito.when(addressMapperMock.toDTO(any(Address.class))).thenReturn(AddressDTOCreator.createdValidUpdatedAddressDTO());

        assertThatCode(() -> addressController.addressPatch(1L , AddressDTOCreator.patch_AddressDTO()))
                .doesNotThrowAnyException();

        ResponseEntity<AddressDTO> response = addressController.addressPatch(1L , AddressDTOCreator.patch_AddressDTO());

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStreet()).isEqualTo(expectedStreetUpdated);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}