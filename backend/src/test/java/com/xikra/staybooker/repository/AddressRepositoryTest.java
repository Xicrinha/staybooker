package com.xikra.staybooker.repository;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.util.entityCreator.AddressCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("test save method")
    void test_save(){
        Address addressToBeSaved = AddressCreator.createAddressToBeSaved();

        Address savedAddress = addressRepository.save(addressToBeSaved);

        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("test findById method")
    void test_findById() {
        Address addressToBeSaved = AddressCreator.createAddressToBeSaved();
        Address savedAddress = testEntityManager.persistAndFlush(addressToBeSaved);

        Optional<Address> response = addressRepository.findById(savedAddress.getId());

        assertThat(response).isPresent();
        assertThat(response.get().getId()).isEqualTo(savedAddress.getId());
    }
    @Test
    @DisplayName("test findByZipcode method")
    void test_findByZipcode(){
        Address addressToBeSaved = AddressCreator.createAddressToBeSaved();
        Address savedAddress = testEntityManager.persistAndFlush(addressToBeSaved);

        Optional<Address> response = addressRepository.findByZipcode("72830170");

        assertThat(response).isPresent();
        assertThat(response.get().getZipcode()).isEqualTo(savedAddress.getZipcode());
    }


    @Test
    @DisplayName("test findAll method")
    void test_findAll(){
        for(int i =0; i<5; i++){
            addressRepository.save(AddressCreator.createAddressToBeSaved());
        }

        PageRequest pageRequest = PageRequest.of(0,25);
        Page<Address> response = addressRepository.findAll(pageRequest);


        assertThat(response.getContent()).isNotNull();
        assertThat(response.getContent().size()).isEqualTo(5);
    }

    @Test
    @DisplayName("test deleteById method")
    void test_deleteById(){
        Address addressToBeSaved = AddressCreator.createAddressToBeSaved();

        addressRepository.save(addressToBeSaved);

        assertThatCode(() -> addressRepository.deleteById(1L)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("test findAllByCityIsLikeIgnoreCase method")
    void test_findAllByCityIsLikeIgnoreCase(){
        Address addressToBeSaved = AddressCreator.createAddressToBeSaved();
        String expectedCity = "Luziânia";
        addressToBeSaved.setCity(expectedCity);

        addressRepository.save(addressToBeSaved);

        PageRequest pageRequest = PageRequest.of(0,25);
        Page<Address> response = addressRepository.findAllByCityIsLikeIgnoreCase("%luziânia%", pageRequest);

        assertThat(response).isNotNull();
        assertThat(response.getContent().get(0).getCity()).isEqualTo(expectedCity);
    }

    @Test
    @DisplayName("test findAllByState method")
    void test_findAllByState(){
        Address addressToBeSaved = AddressCreator.createAddressToBeSaved();
        String expectedState = "GO";
        addressToBeSaved.setState(expectedState);

        addressRepository.save(addressToBeSaved);

        PageRequest pageRequest = PageRequest.of(0,25);
        Page<Address> response = addressRepository.findAllByState(expectedState, pageRequest);

        assertThat(response).isNotNull();
        assertThat(response.getContent().get(0).getState()).isEqualTo(expectedState);
    }

    @Test
    @DisplayName("test findAllByCityIsLikeIgnoreCaseAndState method")
    void test_findAllByCityIsLikeIgnoreCaseAndState(){
        Address addressToBeSaved = AddressCreator.createAddressToBeSaved();
        String expectedState = "GO";
        String expectedCity = "Luziânia";
        addressToBeSaved.setState(expectedState);
        addressToBeSaved.setCity(expectedCity);

        addressRepository.save(addressToBeSaved);

        PageRequest pageRequest = PageRequest.of(0,25);
        Page<Address> response = addressRepository.findAllByCityIsLikeIgnoreCaseAndState("%luziânia%","GO", pageRequest);

        assertThat(response).isNotNull();
        assertThat(response.getContent().get(0).getState()).isEqualTo(expectedState);
        assertThat(response.getContent().get(0).getCity()).isEqualTo(expectedCity);
    }
}