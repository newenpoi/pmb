package com.openclassrooms.newenpoi.pmb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dto.ExtendedUserDTO;
import com.openclassrooms.newenpoi.pmb.dto.UserForm;
import com.openclassrooms.newenpoi.pmb.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ProfileController {
	
	private final UserService userService;
	
	@GetMapping("/profile")
	public ModelAndView getProfile(Authentication authentication) {
		
		User u = userService.recupererUtilisateur(authentication.getName());
		ExtendedUserDTO dto = new ExtendedUserDTO(u);
		
		ModelAndView mav = new ModelAndView("profile");
		mav.addObject("user", dto);
		
		return mav;
	}
	
	@PostMapping("/profile/update")
	public ModelAndView updateProfile(@ModelAttribute UserForm userForm, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		// Récupère l'utilisateur authentifié par son email.
		User u = userService.recupererUtilisateur(authentication.getName());
		
		// Modifie le profil.
		User modified = userService.modifierProfil(u.getId(), userForm);
		ExtendedUserDTO dto = new ExtendedUserDTO(modified);
		
		// Définie l'attribut sur le modèle à passer à la vue.
		redirectAttributes.addFlashAttribute("modification", dto);
		
		// Redirige vers la vue.
		return new ModelAndView("redirect:/profile");
	}
}
