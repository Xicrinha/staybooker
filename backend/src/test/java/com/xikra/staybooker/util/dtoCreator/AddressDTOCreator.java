package com.xikra.staybooker.util.dtoCreator;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.model.AddressDTO;

public class AddressDTOCreator {

    public static AddressDTO post_AddressDTO(){
        return AddressDTO.builder()
                .street("Rua das maravilhas milagrosas")
                .number("31 D")
                .city("Canareiros")
                .state("RJ")
                .zipcode("72830170")
                .build();
    }
    public static AddressDTO createdValidAddressDTO(){
        return AddressDTO.builder()
                .id(1L)
                .street("Rua das maravilhas milagrosas")
                .number("31 D")
                .city("Canareiros")
                .state("RJ")
                .zipcode("72830170")
                .build();
    }
}
