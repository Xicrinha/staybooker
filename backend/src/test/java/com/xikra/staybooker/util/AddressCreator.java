package com.xikra.staybooker.util;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.model.AddressDTO;

public class AddressCreator {

    public static Address createAddressToBeSaved(){
         return Address.builder()
                .street("Rua das maravilhas milagrosas")
                 .number("31 D")
                .city("Canareiros")
                .state("RJ")
                .zipcode("72830170")
                .build();
    }

    public static Address createdValidAddress(){
        return Address.builder()
                .id(1L)
                .street("Rua das maravilhas milagrosas")
                .number("31 D")
                .city("Canareiros")
                .state("RJ")
                .zipcode("72830170")
                .build();
    }

    public static Address createdValidUpdatedAddress(){
        return Address.builder()
                .id(1L)
                .street("Rua das maravilhas incriveis")
                .number("31 D")
                .city("Canareiros")
                .state("RJ")
                .zipcode("72830170")
                .build();
    }
}
