package com.openclassrooms.newenpoi.pmb.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.newenpoi.pmb.business.Address;
import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dao.AddressDao;
import com.openclassrooms.newenpoi.pmb.dao.UserDao;
import com.openclassrooms.newenpoi.pmb.dto.UserForm;
import com.openclassrooms.newenpoi.pmb.service.UserService;
import com.openclassrooms.newenpoi.pmb.util.DateUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserDao userDao;
	private final AddressDao addressDao;
	
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
		User contact = userDao.findByEmail(email);
		
		// Si l'utilisateur n'existe pas ou le contact n'existe pas.
		if (user == null || contact == null) return null;
		
		user.getContacts().add(contact);
		return userDao.save(user);
	}

	@Override
	public User register(UserForm userForm) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		List<Address> addresses = new ArrayList<>();
		
		LocalDate dob = DateUtils.dobToLocalDate(userForm.getDob());
		User user = new User(userForm.getEmail(), encoder.encode(userForm.getPassword()), userForm.getForename(), userForm.getLastName(), dob);
	    Address address = userForm.getAddress();
	    
	    // Il faut penser à sauver l'adresse avant de l'ajouter à l'utilisateur et éviter un TransientObjectException.
	    address = addressDao.save(address);
	    
	    addresses.add(address);
	    user.setAddresses(addresses);
		
		return userDao.save(user);
	}

	@Override
	public User modifierProfil(Long idUser, UserForm userForm) {
		
		List<Address> addresses = new ArrayList<>();
		User user = userDao.findById(idUser).orElse(null);
		
		if (user == null) return null;
		
		user.setForename(userForm.getForename());
		user.setName(userForm.getLastName());
		
		LocalDate dob = DateUtils.dobToLocalDate(userForm.getDob());
		user.setDob(dob);
		
		Address address = userForm.getAddress();
		addresses.add(address);
		
		user.setAddresses(addresses);
		
		return userDao.save(user);
	}

	@Override
	public User recupererUtilisateur(Long idUser) {
		return userDao.findById(idUser).orElse(null);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findByEmail(email);
        
        if (user == null) throw new UsernameNotFoundException("Les identifiants sont incorrects.");
        
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}
}
