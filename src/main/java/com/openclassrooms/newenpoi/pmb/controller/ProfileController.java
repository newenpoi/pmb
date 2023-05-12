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
public class ProfileController {
	
	private final UserService userService;
	
	@GetMapping("/profile")
	public ModelAndView getProfile() {
		return new ModelAndView("profile");
	}
	
	@PostMapping("/profile/update")
	public RedirectView register(@ModelAttribute UserForm userForm, Model model) {
		// TODO : Authentication process.
		Long idUser = 7L;
		
		User u = userService.modifierProfil(idUser, userForm);
		
		model.addAttribute("modification", u);
		
		return new RedirectView("/profile");
	}
}
