package com.openclassrooms.newenpoi.pmb.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
		PageImpl<Payment> page = new PageImpl<>(paiements);
		when(paymentService.recupererPaiements(any(Pageable.class))).thenReturn(page);
		
		// When.
		PageRequest pageRequest = PageRequest.of(0, PaymentController.NB_PAIEMENT_PAR_PAGE, Sort.by("delivered"));
		ModelAndView mav = paymentController.getPayments(pageRequest, 0, "delivered");
		
		// Then.
		assertEquals("payments", mav.getViewName());
		assertEquals(paiements, mav.getModel().get("paiements"));
		
		// Assertions pour la pagination.
		assertEquals(page.getTotalPages(), mav.getModel().get("payments.totalPages"));
		assertEquals(page.getNumber(), mav.getModel().get("payments.number"));
		assertEquals(page.getTotalElements(), mav.getModel().get("payments.totalElements"));
	}
}
