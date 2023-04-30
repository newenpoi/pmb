package com.openclassrooms.newenpoi.pmb.service;

import java.util.List;

import com.openclassrooms.newenpoi.pmb.business.User;

public interface UserService {
	List<User> recupererContacts(Long idUser);
}
