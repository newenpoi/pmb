package com.openclassrooms.newenpoi.pmb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    
	/**
	 * Renvoie la page de login.
	 * @return
	 */
	@GetMapping("/user-login")
    public ModelAndView connexionGet() {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }
	
	/**
	 * En cas d'authentification échouée.
	 * @return
	 */
	@GetMapping("/user-login/failure")
    public ModelAndView getCustomLoginForm() {
		ModelAndView mav = connexionGet();
        mav.addObject("error", true);
        
        return mav;
    }
	
	/**
	 * Déconnecte l'utilisateur.
	 * @param request
	 * @return
	 */
	@GetMapping("/logout")
	public ModelAndView logoutGet(HttpServletRequest request) {
		
        HttpSession session = request.getSession(false);
        
        if (session != null) session.invalidate();
		
		return new ModelAndView("redirect:/");
	}
}
