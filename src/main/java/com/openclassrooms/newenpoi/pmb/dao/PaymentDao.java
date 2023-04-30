package com.openclassrooms.newenpoi.pmb.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.newenpoi.pmb.business.Payment;

public interface PaymentDao extends JpaRepository<Payment, Long> {
	Page<Payment> findAll(Pageable pageable);
}
