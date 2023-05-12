package com.openclassrooms.newenpoi.pmb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    
	@GetMapping("/user-login")
    public ModelAndView getCustomLoginForm() {
        return new ModelAndView("login");
    }
}
