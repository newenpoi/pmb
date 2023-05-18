package com.openclassrooms.newenpoi.pmb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.openclassrooms.newenpoi.pmb.business.Payment;
import com.openclassrooms.newenpoi.pmb.business.User;

public interface PaymentService {
	Page<Payment> recupererPaiements(User u, Pageable pageable);
	Payment payer(User u, Long connection, String description, double sum);
}
