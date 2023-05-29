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
		
		// Créé la vue.
		ModelAndView mav = new ModelAndView("accounts");
		mav.addObject("currentBalance", u.getBalance());
		mav.addObject("transfert", a);
		
		if (a == null) mav.addObject("error", 1);
		
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
		
		// Créé la vue.
		ModelAndView mav = new ModelAndView("accounts");
		mav.addObject("currentBalance", u.getBalance());
		mav.addObject("credit", a);
		
		if (a == null) mav.addObject("error", 2);
		
		return mav;
	}
}
