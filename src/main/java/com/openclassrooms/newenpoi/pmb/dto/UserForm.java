package com.openclassrooms.newenpoi.pmb.dto;

import com.openclassrooms.newenpoi.pmb.business.Address;

import lombok.Data;

@Data
public class UserForm {
    private String email;
    private String password;
    private String lastName;
    private String forename;
    private String dob;
    private Address address;
}
