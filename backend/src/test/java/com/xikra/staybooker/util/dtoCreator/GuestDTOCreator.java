package com.xikra.staybooker.util.dtoCreator;

import com.xikra.staybooker.model.GuestDTO;

public class GuestDTOCreator {

    public static GuestDTO create_GuestDTO(){
        return GuestDTO.builder()
                .firstname("Matias")
                .lastname("Monteiro")
                .street("Rua das maravilhas milagrosas")
                .number("31 D")
                .city("Canareiros")
                .state("RJ")
                .zipcode("72830170")
                .phoneNumber("(62) 98732-7856")
                .email("matias3214@gmail.com")
                .build();
    }

    public static GuestDTO createdValidGuestDTO(){
        return GuestDTO.builder()
                .id(1L)
                .firstname("Matias")
                .lastname("Monteiro")
                .street("Rua das maravilhas milagrosas")
                .number("31 D")
                .city("Canareiros")
                .state("RJ")
                .zipcode("72830170")
                .phoneNumber("(62) 98732-7856")
                .email("matias3214@gmail.com")
                .build();
    }

}
