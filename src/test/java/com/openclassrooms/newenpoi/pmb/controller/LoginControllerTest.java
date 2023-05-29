package com.openclassrooms.newenpoi.pmb.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginControllerTest {
    
    private MockMvc mock;
    
	@Autowired
	private LoginController loginController;
	
	@Autowired
	private WebApplicationContext context;

	/**
	 * On isole le contrôleur sans charger la totalité du contexte de l'application.
	 */
	@BeforeEach
	public void setup() {
		mock = MockMvcBuilders.standaloneSetup(loginController).build();
		
		// Nécesaire dans le contexte d'authentification (applique un `configurer` dans notre cas spécifique).
		this.mock = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
	}
	
    @Test
    void testConnexionGet_shouldReturnView_ofLogin() throws Exception {
    	mock.perform(get("/user-login")).andExpect(view().name("login")).andExpect(status().isOk());
    }

    @Test
    void testGetCustomLoginForm() throws Exception {
        // Act.
        ModelAndView mav = loginController.getCustomLoginForm();

        // Assert.
        assertEquals("login", mav.getViewName());
        assertEquals(true, mav.getModel().get("error"));
    }
    
    @Test
    public void testLogoutGet() {
        // Arrange.
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);

        // Act.
        ModelAndView mav = loginController.logoutGet(request);

        // Assert.
        assertEquals("redirect:/", mav.getViewName());
        assertNull(request.getSession(false));
    }
}
