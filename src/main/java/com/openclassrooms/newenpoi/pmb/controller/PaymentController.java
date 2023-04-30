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
import com.openclassrooms.newenpoi.pmb.service.PaymentService;
import com.openclassrooms.newenpoi.pmb.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PaymentController {
	
	public final static int NB_PAIEMENT_PAR_PAGE = 7;
	
	private final UserService userService;
	private final PaymentService paymentService;
	
	@GetMapping("/payments")
	public ModelAndView getPayments(
			@PageableDefault(size = NB_PAIEMENT_PAR_PAGE, sort = "delivered") Pageable page,
			@RequestParam(name = "numPage", defaultValue = "0") int numPage,
			@RequestParam(name = "sort", defaultValue = "delivered") String sort) {
		
		// TODO : Authentication process.
		Long idUser = 7L;
		
		ModelAndView mav = new ModelAndView("payments");
		mav.addObject("contacts", userService.recupererContacts(idUser));
		mav.addObject("paiements", mav.addObject("payments", paymentService.recupererPaiements(page.withPage(numPage))));
		
		return mav;
	}
	
	@PostMapping("/payments/pay")
	public RedirectView pay(@RequestParam Long connection, Model model) {
		// TODO : Authentication process.
		Long idUser = 7L;
		
		// Call service.
		Payment p = paymentService.payer(idUser, connection);
		
		// Add data to the model.
		model.addAttribute("payment", p);

		// Redirect to the payments view with a query parameter
		return new RedirectView("/payments");
	}
}
