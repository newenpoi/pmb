package com.openclassrooms.newenpoi.pmb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.newenpoi.pmb.business.Account;
import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.service.AccountService;
import com.openclassrooms.newenpoi.pmb.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AccountController {
	private final AccountService accountService;
	private final UserService userService;
	
	/**
	 * Renvoie les comptes bancaires associés de l'utilisateur.
	 * @param authentication
	 * @return
	 */
	@GetMapping("/accounts")
	public ModelAndView accountGet(Authentication authentication) {
		// Récupère les données métier de l'utilisateur authentifié.
		User u = userService.recupererUtilisateur(authentication.getName());
		
		// Créé la vue.
		ModelAndView mav = new ModelAndView("accounts");
		mav.addObject("currentBalance", u.getBalance());
		mav.addObject("accounts", accountService.recupererComptes(u));
		
		return mav;
	}
	
	/**
	 * Lie un compte bancaire à l'utilisateur.
	 * @param accountNumber
	 * @param label
	 * @param authentication
	 * @return
	 */
	@PostMapping("/accounts/add")
	public ModelAndView accountAdd(@RequestParam String accountNumber, @RequestParam String label, Authentication authentication) {
		// Récupère les données métier de l'utilisateur authentifié.
		User u = userService.recupererUtilisateur(authentication.getName());
		
		// Ajoute le compte via notre couche service.
		Account a = accountService.ajouterCompte(u, accountNumber, label);
		
		// Appelle la vue d'orine.
		ModelAndView mav = accountGet(authentication);
		
		// Ajoute les données au modèle selon le résultat.
		if (a == null) mav.addObject("error", 1);
		else mav.addObject("account", a);
		
		return mav;
	}
	
	/**
	 * Transfert de l'argent vers le compte en banque (une taxe est appliquée).
	 * Dans le cadre de l'exercice on considère qu'un seul compte est lié.
	 * @param sum
	 * @param authentication
	 * @return
	 */
	@PostMapping("/accounts/transfert")
	public ModelAndView accountTransfert(@RequestParam Long idAccount, @RequestParam double sum, Authentication authentication) {
		// Récupère les données métier de l'utilisateur authentifié.
		User u = userService.recupererUtilisateur(authentication.getName());
		
		// Transfert l'argent vers le compte.
		Account a = accountService.transferer(u, sum);
		
		// Appelle la vue d'orine.
		ModelAndView mav = accountGet(authentication);
		
		// Ajoute les données au modèle selon le résultat.
		if (a == null) mav.addObject("error", 2);
		else mav.addObject("transfert", a);
		
		return mav;
	}
	
	/**
	 * Transfert de l'argent depuis le compte bancaire vers le compte utilisateur.
	 * Dans le cadre de l'exercice on considère qu'un seul compte est lié.
	 * @param sum
	 * @param authentication
	 * @return
	 */
	@PostMapping("/accounts/refill")
	public ModelAndView accountCrediter(@RequestParam Long idAccount, @RequestParam double sum, Authentication authentication) {
		// Récupère les données métier de l'utilisateur authentifié.
		User u = userService.recupererUtilisateur(authentication.getName());
		
		// Transfert l'argent vers le compte.
		Account a = accountService.crediter(u, sum);
		
		// Appelle la vue d'orine.
		ModelAndView mav = accountGet(authentication);

		// Ajoute les données au modèle selon le résultat.
		if (a == null) mav.addObject("error", 3);
		else mav.addObject("credit", a);
		
		return mav;
	}
}
