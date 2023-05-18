package com.openclassrooms.newenpoi.pmb.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.newenpoi.pmb.business.Payment;
import com.openclassrooms.newenpoi.pmb.business.User;

public interface PaymentDao extends JpaRepository<Payment, Long> {
	Page<Payment> findBySenderOrReceiver(User sender, User receiver, Pageable pageable);
}
