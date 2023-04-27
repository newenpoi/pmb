package com.openclassrooms.newenpoi.pmb.service.impl;

import java.util.List;

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
	public List<Payment> recupererPaiements() {
		return paymentDao.findAll();
	}
}
