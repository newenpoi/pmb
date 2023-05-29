package com.openclassrooms.newenpoi.pmb.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.newenpoi.pmb.business.Payment;
import com.openclassrooms.newenpoi.pmb.business.User;

import jakarta.transaction.Transactional;

/**
 * On s'assure que les méthodes de la dao soient exécutées dans le contexte transactionnel.
 * Une annotation transactional peut être intégrée au niveau du service.
 * @author Christopher PIHET
 *
 */
@Transactional
public interface PaymentDao extends JpaRepository<Payment, Long> {
	
	/**
	 * Récupère une page de paiements par l'utilisateur qui l'a envoyé ou émit.
	 * @param sender
	 * @param receiver
	 * @param pageable
	 * @return
	 */
	Page<Payment> findBySenderOrReceiver(User sender, User receiver, Pageable pageable);
}
