package com.openclassrooms.newenpoi.pmb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.newenpoi.pmb.business.Account;
import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dao.AccountDao;
import com.openclassrooms.newenpoi.pmb.dao.UserDao;
import com.openclassrooms.newenpoi.pmb.service.AccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
	
	private final AccountDao accountDao;
	private final UserDao userDao;
	
	@Override
	public List<Account> recupererComptes(User u) {
		return accountDao.findByUser(u);
	}

	@Override
	public Account transferer(User u, double sum) {
		// Récupère le premier compte de l'utilisateur dans le cadre de l'exercice.
		Account a = accountDao.findById(u.getAccounts().get(0).getId()).orElse(null);
		
		// Aucun compte n'a été lié.
		if (a == null) return null;
		
		// La somme à transférer ne peut être supérieur au solde de l'utilisateur.
		if (sum > u.getBalance()) return null;
		
		// Applique la taxe pour monétisation de l'application.
		double nouveauSolde = a.getBalance() + ((sum * 0.95));
		a.setBalance(nouveauSolde);
		
		// Redéfinie le solde local de l'utilisateur.
		u.setBalance(u.getBalance() - sum);
		userDao.save(u);
		
		return accountDao.save(a);
	}
	
	@Override
	public Account crediter(User u, double sum) {
		// Récupère le premier compte de l'utilisateur dans le cadre de l'exercice.
		Account a = accountDao.findById(u.getAccounts().get(0).getId()).orElse(null);
		
		// Aucun compte n'a été lié.
		if (a == null) return null;
		
		// La somme à transférer ne peut être supérieur au solde total sur le compte.
		if (sum > a.getBalance()) return null;
		
		// Redéfinie le solde local de l'utilisateur.
		u.setBalance(u.getBalance() + sum);
		userDao.save(u);
		
		// Redéfinie le solde bancaire.
		a.setBalance(a.getBalance() - sum);
		
		return accountDao.save(a);
	}

	@Override
	public Account ajouterCompte(User u, String accountNumber, String label) {
		Account a = new Account(accountNumber, label, 1000);
		a.setUser(u);
		
		return accountDao.save(a);
	}
}
