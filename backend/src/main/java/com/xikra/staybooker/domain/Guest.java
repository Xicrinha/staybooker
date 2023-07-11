package com.xikra.staybooker.domain;

import jakarta.persistence.*;

import java.io.Serializable;

public class Guest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String firstname;
    private String lastname;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adress_id")
    private Adress adress;
    private String telephoneNumber;
    private String email;
}
