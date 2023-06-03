package com.openclassrooms.newenpoi.pmb.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	
	/**
	 * Renvoie une page de paiements de l'utilisateur.
	 * @param page
	 * @param numPage
	 * @param authentication
	 * @return
	 */
	@GetMapping("/payments")
	public ModelAndView getPayments(
			@PageableDefault(size = NB_PAIEMENT_PAR_PAGE, sort = "delivered", direction = Sort.Direction.DESC) Pageable page,
			@RequestParam(name = "numPage", defaultValue = "0") int numPage, Authentication authentication) {
		
		// Je récupère les informations de mon utilisateur authentifié.
		User u = userService.recupererUtilisateur(authentication.getName());
		
		ModelAndView mav = new ModelAndView("payments");
		mav.addObject("contacts", userService.recupererContacts(u.getId()));
		mav.addObject("paiements", mav.addObject("payments", paymentService.recupererPaiements(u, page.withPage(numPage))));
		
		return mav;
	}
	
	/**
	 * Créé un nouveau paiement depuis et vers l'utilisateur (données du formulaire).
	 * @param paymentForm
	 * @param redirectAttributes
	 * @param authentication
	 * @return
	 */
	@PostMapping("/payments/pay")
	public ModelAndView pay(@ModelAttribute("paymentForm") PaymentForm paymentForm, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		// Je récupère les informations de mon utilisateur authentifié.
		User u = userService.recupererUtilisateur(authentication.getName());
		
		// On pense à traiter les différents cas d'erreur si on utilise pas BindingResult.
		if (paymentForm.getConnection() == null || paymentForm.getAmount() == null || paymentForm.getAmount() <= 0 || u.getId() == paymentForm.getConnection()) {
			redirectAttributes.addFlashAttribute("error", "Le formulaire est incomplet ou invalide.");
		}
		else {
			
			// Appelle le service pour procéder au paiement.
			Payment p = paymentService.payer(u, paymentForm.getConnection(), paymentForm.getDescription(), paymentForm.getAmount());

			// Définie les attributs en fonction du résultat de l'expression.
			if (p == null) redirectAttributes.addFlashAttribute("error", "Erreur lors du paiement.");
			else redirectAttributes.addFlashAttribute("payment", p);
		}
		
		// Redirige vers la vue.
		return new ModelAndView("redirect:/payments");
	}
}
