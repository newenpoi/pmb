package com.openclassrooms.newenpoi.pmb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.openclassrooms.newenpoi.pmb.business.Payment;

public interface PaymentService {
	Page<Payment> recupererPaiements(Pageable pageable);
	Payment payer(Long idSender, Long idReceiver);
}
