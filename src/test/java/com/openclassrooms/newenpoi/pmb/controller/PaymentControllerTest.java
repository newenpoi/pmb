package com.openclassrooms.newenpoi.pmb.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.newenpoi.pmb.business.Payment;
import com.openclassrooms.newenpoi.pmb.service.PaymentService;

import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {
	
	@InjectMocks private PaymentController paymentController;
	
	@Mock
	private PaymentService paymentService;
	
	@Test
	public void testGetHome() {
		// Given.
		List<Payment> paiements = Arrays.asList(new Payment(), new Payment());
		when(paymentService.recupererPaiements()).thenReturn(paiements);
		
		// When.
		PageRequest pageRequest = PageRequest.of(0, PaymentController.NB_PAIEMENT_PAR_PAGE, Sort.by("date"));
		ModelAndView mav = paymentController.getPayments(pageRequest, 0, "date");
		
		// Then.
		assertEquals("payments", mav.getViewName());
		assertEquals(paiements, mav.getModel().get("paiements"));
	}
}
