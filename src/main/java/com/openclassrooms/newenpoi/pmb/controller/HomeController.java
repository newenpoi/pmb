package com.openclassrooms.newenpoi.pmb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.newenpoi.pmb.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {
	
	private final UserService userService;
	
	/**
	 * Renvoie la vue de la page d'accueil de l'application.
	 * Ajoute l'utilisateur s'il est connecté dans les objets de la vue.
	 * @param authentication
	 * @return
	 */
	@GetMapping("/")
	public ModelAndView getHome(Authentication authentication) {
		
		ModelAndView mav = new ModelAndView("home");
		
		// authentication.getName() permet la récupération du nom d'utilisateur authentifié, en l'occurrence l'email.
		if (authentication != null) mav.addObject("user", userService.recupererUtilisateurDTO(authentication.getName()));
		
		return mav;
	}
}
