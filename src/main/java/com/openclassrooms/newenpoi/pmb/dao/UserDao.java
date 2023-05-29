package com.openclassrooms.newenpoi.pmb.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.openclassrooms.newenpoi.pmb.business.User;

import jakarta.transaction.Transactional;

/**
 * On s'assure que les méthodes de la dao soient exécutées dans le contexte transactionnel.
 * Une annotation transactional peut être intégrée au niveau du service.
 * @author Christopher PIHET
 *
 */
@Transactional
public interface UserDao extends JpaRepository<User, Long> {
	
	/**
	 * Récupère tous les contacts d'un utilisateur.
	 * La façon dont on gère notre entité nous oblige à passer par une JPQuery.
	 * @param idUser
	 * @return
	 */
	@Query("SELECT c FROM User u JOIN u.contacts c WHERE u.id = :idUser")
	List<User> findContactsByUserId(@Param("idUser") Long idUser);
	
	/**
	 * Récupère une page de contacts d'un utilisateur.
	 * @param idUser
	 * @param pageable
	 * @return
	 */
	@Query("SELECT c FROM User u JOIN u.contacts c WHERE u.id = :idUser")
	Page<User> findContactsByUserId(@Param("idUser") Long idUser, Pageable pageable);
	
	User findByEmail(String email);
}
