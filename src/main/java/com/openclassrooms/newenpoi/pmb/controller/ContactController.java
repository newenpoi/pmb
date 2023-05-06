package com.openclassrooms.newenpoi.pmb.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.openclassrooms.newenpoi.pmb.business.Payment;
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
			@RequestParam(name = "numPage", defaultValue = "0") int numPage,
			@RequestParam(name = "sort", defaultValue = "name") String sort) {
		
		// TODO : Auth.
		Long idUser = 7L;
		
		// Récupère les contacts et les passe à la vue.
		ModelAndView mav = new ModelAndView("contacts");
		mav.addObject("contacts", userService.recupererPageContacts(idUser, page.withPage(numPage)));
		
		// Renvoyer la vue.
		return mav;
	}
	
	@PostMapping("/contacts/add")
	public RedirectView addContact(@RequestParam String email, Model model) {
		// TODO : Authentication process.
		Long idUser = 7L;
		
		// Call service.
		User c = userService.ajouterContact(idUser, email);
		
		// Add data to the model.
		model.addAttribute("contact", c);

		// Redirect to the payments view with a query parameter
		return new RedirectView("/contacts");
	}
}
