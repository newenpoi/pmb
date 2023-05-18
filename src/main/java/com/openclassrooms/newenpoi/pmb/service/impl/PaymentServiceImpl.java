package com.openclassrooms.newenpoi.pmb.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.openclassrooms.newenpoi.pmb.business.Payment;
import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dao.PaymentDao;
import com.openclassrooms.newenpoi.pmb.dao.UserDao;
import com.openclassrooms.newenpoi.pmb.service.PaymentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	public final PaymentDao paymentDao;
	public final UserDao userDao;

	@Override
	public Page<Payment> recupererPaiements(User u, Pageable page) {
		return paymentDao.findBySenderOrReceiver(u, u, page);
	}

	@Override
	public Payment payer(User u, Long connection, String description, double sum) {
		
		double senderBalance = u.getAccounts().get(0).getBalance();
		
		// Check sender balance.
		if (senderBalance < sum) return null;
		
		// Check if receiver exist.
		User receiver = userDao.findById(connection).orElse(null);
		
		// Sets the new balance data for receiver.
		double receiverBalance = receiver.getAccounts().get(0).getBalance();
		receiver.getAccounts().get(0).setBalance(receiverBalance + sum);
		
		// Sets the new balance data for sender.
		u.getAccounts().get(0).setBalance(senderBalance - sum);
		
		// Create a new payment entry.
		Payment p = new Payment(sum, LocalDateTime.now(), description, u, receiver);
		
		// Returns saved payment.
		return paymentDao.save(p);
	}
}
