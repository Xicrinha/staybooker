package com.xikra.staybooker.util.entityCreator;

import com.xikra.staybooker.domain.Guest;
import com.xikra.staybooker.model.GuestDTO;

import java.util.ArrayList;
import java.util.List;

public class GuestCreator {

    public static Guest createGuestToBeSaved(){
        return Guest.builder()
                .firstname("Matias")
                .lastname("Monteiro")
                .address(AddressCreator.createAddressToBeSaved())
                .phoneNumber("(62) 98732-7856")
                .email("matias3214@gmail.com")
                .build();
    }

    public static Guest createdValidGuest(){
        return Guest.builder()
                .id(1L)
                .firstname("Matias")
                .lastname("Monteiro")
                .address(AddressCreator.createAddressToBeSaved())
                .phoneNumber("(62) 98732-7856")
                .email("matias3214@gmail.com")
                .build();
    }

    public static List<Guest> listCreatedValidGuest(){
        List<Guest> guestList = new ArrayList<>();

        for(int i = 0; i < 5; i ++){
            guestList.add(createdValidGuest());
        }

        return guestList;
    }
}
