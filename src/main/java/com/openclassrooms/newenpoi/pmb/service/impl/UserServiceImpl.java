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
		return userDao.findUserContactsById(idUser);
	}

	@Override
	public Page<User> recupererPageContacts(Long idUser, Pageable pageable) {
		return userDao.findUserPage(idUser, pageable);
	}
}
