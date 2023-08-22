package com.xikra.staybooker.service;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.repository.AddressRepository;
import com.xikra.staybooker.util.AddressCreator;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Log4j
class AddressServiceTest {

    @Mock
    AddressRepository addressRepository;

    @InjectMocks
    AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("getAllAddress no Param return list of address inside page object when successful")
    void getAllAddress_NoParam_ReturnsListOfAddressInsidePageObject_WhenSuccessful(){
        PageRequest pageRequest = PageRequest.of(0,25);
        Page<Address> addressPage = new PageImpl<>(AddressCreator.listCreatedValidAddress());

        when(addressRepository.findAll(pageRequest)).thenReturn(addressPage);

        Page<Address> response = addressService.getAllAddress(null,null,null, null);

        assertThat(response.getSize()).isEqualTo(5);
    }

    @Test
    @DisplayName("getAllAddress City Param return list of address inside page object when successful")
    void getAllAddress_CityParam_ReturnsListOfAddressInsidePageObject_WhenSuccessful(){
        PageRequest pageRequest = PageRequest.of(0, 25);
        String cityParam = "Luziânia";

        Address address = AddressCreator.createdValidAddress();
        address.setCity(cityParam);
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);

        Page<Address> addressPage = new PageImpl<>(addressList);

        when(addressRepository.findAllByCityIsLikeIgnoreCase(cityParam, pageRequest)).thenReturn(addressPage);

        Page<Address> response = addressService.getAllAddress(cityParam, "", null, null);

        assertThat(response.getSize()).isEqualTo(1);
        assertThat(response.getContent().get(0).getCity()).isEqualTo(cityParam);
    }

    @Test
    @DisplayName("getAllAddress state param return list of address inside page object when successful")
    void getAllAddress_StateParam_ReturnsListOfAddressInsidePageObject_WhenSuccessful(){
        PageRequest pageRequest = PageRequest.of(0, 25);
        String stateParam = "SP";

        Address address = AddressCreator.createdValidAddress();
        address.setState(stateParam);
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);

        Page<Address> addressPage = new PageImpl<>(addressList);

        when(addressRepository.findAllByState(stateParam, pageRequest)).thenReturn(addressPage);

        Page<Address> response = addressService.getAllAddress("", stateParam, null, null);

        assertThat(response.getSize()).isEqualTo(1);
        assertThat(response.getContent().get(0).getState()).isEqualTo(stateParam);
    }

    @Test
    @DisplayName("getAllAddress city and state param return list of address inside page object when successful")
    void getAllAddress_CityAndStateParam_ReturnsListOfAddressInsidePageObject_WhenSuccessful(){
        PageRequest pageRequest = PageRequest.of(0, 25);
        String stateParam = "GO";
        String cityParam = "Luziânia";

        Address address = AddressCreator.createdValidAddress();
        address.setState(stateParam);
        address.setCity(cityParam);
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);

        Page<Address> addressPage = new PageImpl<>(addressList);

        when(addressRepository.findAllByCityIsLikeIgnoreCaseAndState(cityParam,stateParam, pageRequest)).thenReturn(addressPage);

        Page<Address> response = addressService.getAllAddress(cityParam, stateParam, null, null);

        assertThat(response.getSize()).isEqualTo(1);
        assertThat(response.getContent().get(0).getState()).isEqualTo(stateParam);
        assertThat(response.getContent().get(0).getCity()).isEqualTo(cityParam);
    }

    @Test
    @DisplayName("Find by id returns address when successful")
    void findById_ReturnsAddress_WhenSuccessful(){
        Long expectedId = AddressCreator.createdValidAddress().getId();

        when(addressRepository.findById(1L)).thenReturn(Optional.ofNullable(AddressCreator.createdValidAddress()));

        Optional<Address> response = addressService.getAddresById(1L);

        assertThat(response).isNotNull();
        assertThat(response.get().getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save returns address when successful")
    void save_ReturnsAddress_WhenSuccessful() {
        Address addressToBeSaved = AddressCreator.createAddressToBeSaved();

        when(addressRepository.save(any(Address.class)))
                .thenReturn(AddressCreator.createdValidAddress());

        Address response = addressService.createAddress(addressToBeSaved);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("update returns address when successful")
    void update_ReturnAddress_WhenSuccessful() {
        String expectedStreet = "Rua Epaminondas III";
        Address addressToBeUpdate = AddressCreator.createAddressToBeSaved();
        addressToBeUpdate.setStreet(expectedStreet);

        when(addressRepository.findById(1L)).thenReturn(Optional.ofNullable(AddressCreator.createdValidAddress()));

        Address addressUpdated = AddressCreator.createdValidAddress();
        addressUpdated.setStreet(expectedStreet);

        when(addressRepository.save(any(Address.class)))
                .thenReturn(addressUpdated);

        Address response = addressService.updateAddress(1L, addressToBeUpdate);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getStreet()).isEqualTo(expectedStreet);
    }

    @Test
    @DisplayName("delete remove address when successful")
    void delete_RemovesAddress_WhenSuccessful() {
        doNothing().when(addressRepository).deleteById(1L);

        assertThatCode(() -> addressService.deleteAddress(1L))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("patch returns address when successful")
    void patch_ReturnAddress_WhenSuccessful() {
        String expectedStreet = "Rua Epaminondas III";
        Address addressToBeUpdate = new Address();
        addressToBeUpdate.setStreet(expectedStreet);

        when(addressRepository.findById(1L)).thenReturn(Optional.ofNullable(AddressCreator.createdValidAddress()));

        Address addressUpdated = AddressCreator.createdValidAddress();
        addressUpdated.setStreet(expectedStreet);

        when(addressRepository.save(any(Address.class)))
                .thenReturn(addressUpdated);

        Address response = addressService.addressPatch(1L, addressToBeUpdate);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getStreet()).isEqualTo(expectedStreet);
    }
}