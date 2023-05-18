package com.openclassrooms.newenpoi.pmb.controller;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.newenpoi.pmb.service.PaymentService;
import com.openclassrooms.newenpoi.pmb.service.UserService;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {
	
	@MockBean
	private PaymentService paymentService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	@Autowired
	private MockMvc mock;
	
	@Test
	@Disabled
	public void testGetHome() throws Exception {
		fail("Not implemented yet...");
	}
	
	/*
	@Test
	@WithUserDetails("loid.forger@eden.com")
	public void testGetHome() {
		// Given.
		List<Payment> paiements = Arrays.asList(new Payment(), new Payment());
		PageImpl<Payment> page = new PageImpl<>(paiements);
		when(paymentService.recupererPaiements(any(User.class), any(Pageable.class))).thenReturn(page);
		
		// When.
		PageRequest pageRequest = PageRequest.of(0, PaymentController.NB_PAIEMENT_PAR_PAGE, Sort.by("delivered"));
		ModelAndView mav = paymentController.getPayments(pageRequest, 0, "delivered", );
		
		// Then.
		assertEquals("payments", mav.getViewName());
		assertEquals(paiements, mav.getModel().get("paiements"));
		
		// Assertions pour la pagination.
		assertEquals(page.getTotalPages(), mav.getModel().get("payments.totalPages"));
		assertEquals(page.getNumber(), mav.getModel().get("payments.number"));
		assertEquals(page.getTotalElements(), mav.getModel().get("payments.totalElements"));
	}
	*/
}
