package com.openclassrooms.newenpoi.pmb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    
	@GetMapping("/user-login")
    public ModelAndView connexionGet() {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }
	
	@GetMapping("/user-login/failure")
    public ModelAndView getCustomLoginForm() {
		ModelAndView mav = connexionGet();
        mav.addObject("error", true);
        
        return mav;
    }
}
