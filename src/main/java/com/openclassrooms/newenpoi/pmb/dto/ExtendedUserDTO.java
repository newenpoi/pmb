package com.openclassrooms.newenpoi.pmb.dto;

import java.time.LocalDate;

import com.openclassrooms.newenpoi.pmb.business.Address;
import com.openclassrooms.newenpoi.pmb.business.User;

import lombok.Data;

@Data
public class ExtendedUserDTO {
    private Long id;
    private String email;
    private String name;
    private String forename;
    private LocalDate dob;
    private Address address;

    public ExtendedUserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.forename = user.getForename();
        this.dob = user.getDob();
        
        // Cette DTO ne renvoie que la première adresse.
        this.address = user.getAddresses().get(0);
    }
}
