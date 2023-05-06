package com.openclassrooms.newenpoi.pmb.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.openclassrooms.newenpoi.pmb.business.User;

public interface UserService {
	List<User> recupererContacts(Long idUser);
	Page<User> recupererPageContacts(Long idUser, Pageable pageable);
	
	User ajouterContact(Long idUser, String email);
}
