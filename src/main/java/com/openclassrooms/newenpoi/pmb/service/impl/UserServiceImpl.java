package com.openclassrooms.newenpoi.pmb.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dao.UserDao;
import com.openclassrooms.newenpoi.pmb.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	
	@Override
	public List<User> recupererContacts(Long idUser) {
		
		List<User> users = userDao.findUserContactsById(idUser);
		
		return users;
	}

	@Override
	public Page<User> recupererPageContacts(Long idUser, Pageable pageable) {
		return userDao.findUserPage(idUser, pageable);
	}

	@Override
	public User ajouterContact(Long idUser, String email) {
		User user = userDao.findById(idUser).orElse(null);
		User contact = userDao.findByEmail();
		
		// Si l'utilisateur n'existe pas ou le contact n'existe pas.
		if (user == null || contact == null) return null;
		
		user.getContacts().add(contact);
		return userDao.save(user);
	}
}
