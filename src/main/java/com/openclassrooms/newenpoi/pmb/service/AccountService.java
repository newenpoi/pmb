package com.openclassrooms.newenpoi.pmb.service;

import java.util.List;

import com.openclassrooms.newenpoi.pmb.business.Account;
import com.openclassrooms.newenpoi.pmb.business.User;

public interface AccountService {
	List<Account> recupererComptes(User u);
	Account transferer(User u, double sum);
	Account crediter(User u, double sum);
	Account ajouterCompte(User u, String accountNumber, String label);
}
