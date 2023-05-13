package com.openclassrooms.newenpoi.pmb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.newenpoi.pmb.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {
	
	private final UserService userService;
	
	@GetMapping("/")
	public ModelAndView getHome() {
		
		ModelAndView mav = new ModelAndView("home");
		
		// TODO : Authentication process.
		Long idUser = 7L;
		
		mav.addObject("user", userService.recupererUtilisateur(idUser));
		
		return mav;
	}
}
