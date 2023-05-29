package com.openclassrooms.newenpoi.pmb.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ContactController {
	
	public final static int NB_CONTACT_PAR_PAGE = 7;
	private final UserService userService;
	
	@GetMapping("/contacts")
	public ModelAndView getContacts(
			@PageableDefault(size = NB_CONTACT_PAR_PAGE, sort = "name") Pageable page,
			@RequestParam(name = "numPage", defaultValue = "0") int numPage, Authentication authentication) {
		
		User u = userService.recupererUtilisateur(authentication.getName());
		Page<User> contacts = userService.recupererPageContacts(u.getId(), page.withPage(numPage));
		
		// Récupère les contacts et les passe à la vue.
		ModelAndView mav = new ModelAndView("contacts");
		mav.addObject("contacts", contacts);
		
		// Renvoyer la vue.
		return mav;
	}
	
	@GetMapping("/contacts/delete")
	public ModelAndView deleteContact(@RequestParam Long idContact, RedirectAttributes redirectAttributes, Authentication authentication) {
		User u = userService.supprimerContact(userService.recupererUtilisateur(authentication.getName()), idContact);
		
		// Ajoute un nouveau flash attribute en cas d'erreur.
		if (u == null) redirectAttributes.addFlashAttribute("error", 3);
		
		// Redirige.
		return new ModelAndView("redirect:/contacts");
	}
	
	@PostMapping("/contacts/add")
	public ModelAndView addContact(@RequestParam String email, RedirectAttributes redirectAttributes, Authentication authentication) {
				
		if (email.equals(authentication.getName())) {
			// On ne peut s'ajouter soi même.
			redirectAttributes.addFlashAttribute("error", 1);
		}
		else {
			// Ajoute le contact.
			User c = userService.ajouterContact(authentication.getName(), email);
			
			// Spécifie l'erreur ou le contact ajouté dans les attributs.
			if (c == null) redirectAttributes.addFlashAttribute("error", 2);
			else redirectAttributes.addFlashAttribute("contact", email);
		}

		// Redirige.
		return new ModelAndView("redirect:/contacts");
	}
}
