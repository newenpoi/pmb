package com.openclassrooms.newenpoi.pmb.dto;

import com.openclassrooms.newenpoi.pmb.business.User;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String forename;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.forename = user.getForename();
    }
}
