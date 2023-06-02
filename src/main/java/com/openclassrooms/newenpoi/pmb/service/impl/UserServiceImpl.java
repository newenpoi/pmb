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
import com.openclassrooms.newenpoi.pmb.dto.UserDTO;
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
		return userDao.findContactsByUserId(idUser);
	}

	@Override
	public Page<User> recupererPageContacts(Long idUser, Pageable pageable) {
		return userDao.findContactsByUserId(idUser, pageable);
	}

	@Override
	public User ajouterContact(String emailUser, String emailContact) {
		User user = userDao.findByEmail(emailUser);
		User contact = userDao.findByEmail(emailContact);
		
		// Si l'utilisateur et le contact existent et que l'utilisateur contient déjà le contact.
		if (user != null && contact != null && user.getContacts().contains(contact)) return null;
		
		// Si l'utilisateur n'existe pas ou le contact n'existe pas.
		if (user == null || contact == null) return null;
		
		user.getContacts().add(contact);
		
		return userDao.save(user);
	}
	
	/**
	 * Récupère et supprime le contact d'un utilisateur donné.
	 */
	@Override
	public User supprimerContact(User u, Long idContact) {
		// Récupère le contact qu'on souhaite supprimer.
		User contact = userDao.findById(idContact).orElse(null);
		if (contact == null) return null;
		
		// Supprimer le contact de notre utilisateur.
		u.getContacts().remove(contact);
		
		// Persistance des données.
		return userDao.save(u);
	}

	/**
	 * Remarque, pour le moment présent on s'occupe de gérer une seule adresse postale.
	 */
	@Override
	public User register(UserForm userForm) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		List<Address> addresses = new ArrayList<>();
		
		LocalDate dob = DateUtils.dobToLocalDate(userForm.getDob());
		User user = new User(userForm.getEmail(), encoder.encode(userForm.getPassword()), userForm.getLastName(), userForm.getForename(), dob);
	    Address address = userForm.getAddress();

	    // Il faut penser à sauver l'adresse avant de l'ajouter à l'utilisateur et éviter un TransientObjectException.
	    address = addressDao.save(address);
	    
	    addresses.add(address);
	    user.setAddresses(addresses);

		return userDao.save(user);
	}

	/**
	 * On exploite qu'une seule addresse dans le cadre de l'exercice.
	 */
	@Override
	public User modifierProfil(Long idUser, UserForm userForm) {
		
		User user = userDao.findById(idUser).orElse(null);
		
		if (user == null) return null;
		
		user.setForename(userForm.getForename());
		user.setName(userForm.getLastName());
		
		// LocalDate dob = DateUtils.dobToLocalDate(userForm.getDob());
		// user.setDob(dob);
		
		Address address = userForm.getAddress();
		
		user.getAddresses().get(0).setNumber(address.getNumber());
		user.getAddresses().get(0).setStreet(address.getStreet());
		user.getAddresses().get(0).setExtra(address.getExtra());
		user.getAddresses().get(0).setZipCode(address.getZipCode());
		user.getAddresses().get(0).setCity(address.getCity());
		
		return userDao.save(user);
	}

	@Override
	public User recupererUtilisateur(Long idUser) {
		return userDao.findById(idUser).orElse(null);
	}
	
	@Override
	public User recupererUtilisateur(String email) {
		return userDao.findByEmail(email);
	}
	
	@Override
	public UserDTO recupererUtilisateurDTO(String email) {
		User u = userDao.findByEmail(email);
		
		if (u != null) return new UserDTO(u);
		return null;
	}

	/**
	 * Cette méthode est privilégiée pour la gestion d'un custom UserDetails.
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findByEmail(email);
        
        if (user == null) throw new UsernameNotFoundException("Les identifiants sont incorrects.");
        
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}
}
