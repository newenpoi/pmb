package com.openclassrooms.newenpoi.pmb.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.openclassrooms.newenpoi.pmb.business.User;

public interface UserDao extends JpaRepository<User, Long> {
	@Query("SELECT c FROM User u JOIN u.contacts c WHERE u.id = :idUser")
	List<User> findUserContactsById(@Param("idUser") Long idUser);
	
	@Query("SELECT c FROM User u JOIN u.contacts c WHERE u.id = :idUser")
	Page<User> findUserPage(@Param("idUser") Long idUser, Pageable pageable);
}
