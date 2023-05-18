package com.openclassrooms.newenpoi.pmb.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.openclassrooms.newenpoi.pmb.business.Payment;
import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dto.PaymentForm;
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
			@RequestParam(name = "sort", defaultValue = "delivered") String sort, Authentication authentication) {
		
		User u = userService.recupererUtilisateur(authentication.getName());
		
		ModelAndView mav = new ModelAndView("payments");
		mav.addObject("contacts", userService.recupererContacts(u.getId()));
		mav.addObject("paiements", mav.addObject("payments", paymentService.recupererPaiements(u, page.withPage(numPage))));
		
		return mav;
	}
	
	@PostMapping("/payments/pay")
	public ModelAndView pay(@ModelAttribute("paymentForm") PaymentForm paymentForm, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		// Récupère l'utilisateur authentifié.
		User u = userService.recupererUtilisateur(authentication.getName());
		
		// On ne peut s'envoyer de l'argent à soi-même.
		if (u.getId() == paymentForm.getConnection()) redirectAttributes.addFlashAttribute("error", true);
		else {
			
			// Appelle le service pour procéder au paiement.
			Payment p = paymentService.payer(u, paymentForm.getConnection(), paymentForm.getDescription(), paymentForm.getAmount());
			
			// Définie les attributs en fonction du résultat de l'expression.
			if (p == null) redirectAttributes.addFlashAttribute("error", true);
			else redirectAttributes.addFlashAttribute("payment", p);
		}
		
		// Redirige vers la vue.
		return new ModelAndView("redirect:/payments");
	}
}
