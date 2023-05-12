package com.openclassrooms.newenpoi.pmb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dto.UserForm;
import com.openclassrooms.newenpoi.pmb.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class RegisterController {
	
	private final UserService userService;
	
	@GetMapping("/register")
	public ModelAndView getRegisterPage() {
		return new ModelAndView("register");
	}
	
	/**
	 * UserForm (DTO) sera notre intermédaire entre les entités modèle et les données du formulaire.
	 * @param userForm
	 * @param model
	 * @return
	 */
	@PostMapping("/register/validate")
	public RedirectView register(@ModelAttribute UserForm userForm, Model model) {
		User u = userService.register(userForm);
		
		model.addAttribute("registration", u);
		
		return new RedirectView("/register");
	}
}
