package com.practice.lld.domain;


import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Data
public class Contact extends AbstractContactT {

    private String firstname;
    private String lastname;
    private String email;
    private String phone;

}
