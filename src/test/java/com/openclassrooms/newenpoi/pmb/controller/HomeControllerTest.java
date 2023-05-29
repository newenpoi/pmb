package com.openclassrooms.newenpoi.pmb.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * Tous les beans et configurations seront disponibles pendant les tests.
 * @author Christopher PIHET
 *
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HomeControllerTest {
	
    private MockMvc mock;
    
	@Autowired
	private HomeController indexHomeController;
	
	@Autowired
	private WebApplicationContext context;
	
    @Mock
    private UserDetailsService userDetailsService;
	
	/**
	 * On isole le contrôleur sans charger la totalité du contexte de l'application.
	 */
	@BeforeEach
	public void setup() {
		mock = MockMvcBuilders.standaloneSetup(indexHomeController).build();
		
		// Nécesaire dans le contexte d'authentification (applique un `configurer` dans notre cas spécifique).
		this.mock = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
	}
	
	@Test
	public void testHomeController_shouldReturnView_ofHome() throws Exception {
		mock.perform(get("/")).andExpect(view().name("home")).andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Simule un utilisateur authentifié.")
	public void givenAuthenticatedUser_whenGetHome_thenReturnHomeView() throws Exception {
	    
        // Given.
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("loid.forger@eden.com", "logme1nn", new ArrayList<>());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        
        // Définie l'authentification dans le contexte SecurityContextHolder.
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Then.
		mock.perform(get("/")).andExpect(view().name("home")).andExpect(model().attributeExists("user")).andExpect(status().isOk());
		
		// With (with(user)).
		// mock.perform(get("/").with(user("loid.forger@eden.com").password("logme1nn"))).andExpect(view().name("home")).andExpect(model().attributeExists("user")).andExpect(status().isOk());
	}
}
