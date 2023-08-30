package com.xikra.staybooker.util.entityCreator;

import com.xikra.staybooker.domain.Address;
import com.xikra.staybooker.model.AddressDTO;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Address> listCreatedValidAddress(){
        List<Address> addressList = new ArrayList<>();

        for(int i = 0; i < 5; i ++){
            addressList.add(createdValidAddress());
        }

        return addressList;
    }
}
