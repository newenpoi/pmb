package com.openclassrooms.newenpoi.pmb.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.newenpoi.pmb.service.PaymentService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PaymentController {
	
	private final static int NB_PAIEMENT_PAR_PAGE = 7;
	private final PaymentService paymentService;
	
	@GetMapping("/")
	public ModelAndView getHome(
			@PageableDefault(size = NB_PAIEMENT_PAR_PAGE, sort = "date") Pageable page,
			@RequestParam(name = "numPage", defaultValue = "0") int numPage,
			@RequestParam(name = "sort", defaultValue = "date") String sort) {
		
		ModelAndView mav = new ModelAndView("home");
		
		mav.addObject("message", "_hello_World!");
		mav.addObject("paiements", paymentService.recupererPaiements());
		
		return mav;
	}
}
