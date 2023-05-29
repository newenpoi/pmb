package com.openclassrooms.newenpoi.pmb.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openclassrooms.newenpoi.pmb.business.Payment;
import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dao.PaymentDao;
import com.openclassrooms.newenpoi.pmb.dao.UserDao;
import com.openclassrooms.newenpoi.pmb.service.PaymentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	private final PaymentDao paymentDao;
	private final UserDao userDao;

	@Override
	public Page<Payment> recupererPaiements(User u, Pageable page) {
		return paymentDao.findBySenderOrReceiver(u, u, page);
	}

	/**
	 * On va juste effectuer le paiement et le solde utilisateur sera à jour.
	 */
	@Override
	public Payment payer(User u, Long connection, String description, double sum) {
		
		// Vérifie le solde de l'émetteur.
		if (u.getBalance() < sum) return null;
		
		// Vérifie que le receveur existe.
		User receiver = userDao.findById(connection).orElse(null);
		
		// Si notre utilisateur n'existe pas.
		if (receiver == null) return null;
		
		// Redéfinie les soldes local des utilisateurs.
		u.setBalance(u.getBalance() - sum);
		receiver.setBalance(receiver.getBalance() + sum);
		
		// Sauvegarde le nouveau solde des parties.
		userDao.saveAll(Arrays.asList(u, receiver));
		
		// Création d'un nouveau paiement.
		Payment p = new Payment(sum, LocalDateTime.now(), description, u, receiver);
		
		// Persistance.
		return paymentDao.save(p);
	}
}
