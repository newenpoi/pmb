package com.openclassrooms.newenpoi.pmb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassrooms.newenpoi.pmb.dto.UserForm;
import com.openclassrooms.newenpoi.pmb.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class RegisterController {
	
	private final UserService userService;
	
	@GetMapping("/register")
	public ModelAndView inscriptionGet(@ModelAttribute UserForm userForm) {
		ModelAndView mav = new ModelAndView("register");
		mav.addObject("utilisateur", userForm);
		return mav;
	}
	
	/**
	 * UserForm (DTO) sera notre intermédaire entre les entités modèle et les données du formulaire.
	 * @param userForm
	 * @param model
	 * @return
	 */
	@PostMapping("/register/validate")
	public ModelAndView register(@Valid @ModelAttribute UserForm userForm, BindingResult result, RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			ModelAndView mav = inscriptionGet(userForm);
			return mav;
		}
		
		userService.register(userForm);
		redirectAttributes.addFlashAttribute("inscription", true);
		
		return new ModelAndView("redirect:/");
	}
}
