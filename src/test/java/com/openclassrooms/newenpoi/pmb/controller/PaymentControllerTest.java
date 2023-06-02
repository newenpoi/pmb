package com.openclassrooms.newenpoi.pmb.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.newenpoi.pmb.business.Payment;
import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dto.PaymentForm;
import com.openclassrooms.newenpoi.pmb.service.PaymentService;
import com.openclassrooms.newenpoi.pmb.service.UserService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentControllerTest {
	
    private MockMvc mock;
    
    @Autowired
	private PaymentController paymentController;
	
	@Autowired
	private WebApplicationContext context;
	
    @Mock
    private UserService userService;
    
	@MockBean
    private PaymentService paymentService;
	
	/**
	 * On isole le contrôleur sans charger la totalité du contexte de l'application.
	 */
	@BeforeEach
	public void setup() {
		mock = MockMvcBuilders.standaloneSetup(paymentController).build();
		
		// Nécesaire dans le contexte d'authentification (applique un `configurer` dans notre cas spécifique).
		this.mock = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
	}
	
	
	@Test
	@DisplayName("Teste qu'on ai bien une liste de paiements dans la vue ainsi que le bon code statut.")
    public void testGetPayments_shouldReturnListPayments_ofUser() throws Exception {
		// Given.
		User sender = new User(), receiver = new User();
		sender.setId(1L);
		sender.setName("Forger");
		
		receiver.setId(2L);
		receiver.setName("Hawk");
		
		List<User> contacts = new ArrayList<User>();
		List<Payment> paiements = Arrays.asList(new Payment(10, LocalDateTime.now(), "Épinards", sender, receiver));
		PageImpl<Payment> page = new PageImpl<>(paiements);
		
        when(userService.recupererUtilisateur("loid.forger@eden.com")).thenReturn(sender);
        when(userService.recupererContacts(sender.getId())).thenReturn(contacts);
        when(paymentService.recupererPaiements(any(User.class), any(Pageable.class))).thenReturn(page);
		
		// Act and Assert.
		mock.perform(get("/payments").with(user("loid.forger@eden.com").password("logme1nn"))).andExpect(view().name("payments")).andExpect(model().attributeExists("payments")).andExpect(status().isOk());
		
		// Effectuer d'autres tests si possible.
    }
	
    @Test
    @DisplayName("Test qu'un paiement s'effectue avec succès.")
    public void testAddPayment_shouldReturnSinglePayment() throws Exception {
    	// Arrange.
    	Payment p = new Payment();
        PaymentForm paymentForm = new PaymentForm();
        
        paymentForm.setAmount(50D);
        paymentForm.setConnection(7L);
    	
        when(paymentService.payer(any(User.class), any(Long.class), any(String.class), anyDouble())).thenReturn(p);

        // Act and Assert.
        mock.perform(post("/payments/pay").flashAttr("paymentForm", paymentForm).with(user("loid.forger@eden.com").password("logme1nn"))).andExpect(redirectedUrl("/payments"));
    }
}
