package com.openclassrooms.newenpoi.pmb.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openclassrooms.newenpoi.pmb.business.Payment;
import com.openclassrooms.newenpoi.pmb.dao.PaymentDao;
import com.openclassrooms.newenpoi.pmb.service.PaymentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	public final PaymentDao paymentDao;

	@Override
	public Page<Payment> recupererPaiements(Pageable page) {
		return paymentDao.findAll(page);
	}

	@Override
	public Payment payer(Long idSender, Long idReceiver) {
		// Check sender balance.
		
		// Pay.
		
		// TODO Auto-generated method stub
		return null;
	}
}
