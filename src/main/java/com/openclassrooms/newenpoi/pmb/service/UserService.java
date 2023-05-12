package com.openclassrooms.newenpoi.pmb.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dto.UserForm;

public interface UserService {
	List<User> recupererContacts(Long idUser);
	Page<User> recupererPageContacts(Long idUser, Pageable pageable);
	
	User ajouterContact(Long idUser, String email);
	User register(UserForm userForm);
	User modifierProfil(Long idUser, UserForm userForm);
	User recupererUtilisateur(Long idUser);
}
