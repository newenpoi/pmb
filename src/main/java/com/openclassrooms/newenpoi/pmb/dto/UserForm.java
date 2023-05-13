package com.openclassrooms.newenpoi.pmb.dto;

import com.openclassrooms.newenpoi.pmb.business.Address;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserForm {
	@NotBlank(message = "Une adresse mail ne peut être vide.")
	@Email(message = "Cette adresse n'est pas valide.")
	private String email;
    
	@Size(min = 4, message = "Doit avoir au moins quatre caractères.")
	private String password;
    
	@NotBlank(message = "Le nom ne peut être vide.")
	private String lastName;
    
	@NotBlank(message = "Le prénom ne peut être vide.")
	private String forename;
    
	@NotBlank(message = "La date de naissance ne peut être vide.")
	private String dob;
    
	private Address address;
}
