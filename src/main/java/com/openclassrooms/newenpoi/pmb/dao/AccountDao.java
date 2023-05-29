package com.openclassrooms.newenpoi.pmb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.newenpoi.pmb.business.Account;
import com.openclassrooms.newenpoi.pmb.business.User;

import jakarta.transaction.Transactional;

@Transactional
public interface AccountDao extends JpaRepository<Account, Long> {
	List<Account> findByUser(User u);
}
