package com.openclassrooms.newenpoi.pmb.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.newenpoi.pmb.business.Address;
import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dao.UserDao;
import com.openclassrooms.newenpoi.pmb.dto.UserForm;
import com.openclassrooms.newenpoi.pmb.service.UserService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterControllerTest {
	
    private MockMvc mock;
    
    @Autowired
	private RegisterController registerController;
	
	@Autowired
	private WebApplicationContext context;
	
    @Mock
    private UserDetailsService userDetailsService;
    
    /**
     * MockBean dans le contexte de l'application Spring.
     * Il est utilisé pour ajouter une instance mockée (fictive) au contexte de l'application lors des tests d'intégration.
     */
    @MockBean
    private UserService userService;
    
    @Mock
    private UserDao userDao;
	
	/**
	 * On isole le contrôleur sans charger la totalité du contexte de l'application.
	 */
	@BeforeEach
	public void setup() {
		mock = MockMvcBuilders.standaloneSetup(registerController).build();
		
		// Nécesaire dans le contexte d'authentification (applique un `configurer` dans notre cas spécifique).
		this.mock = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
	}
	
	@Test
	public void testInscriptionGet_shouldReturnMav_withUserForm() throws Exception {
		UserForm userForm = new UserForm();
		
		userForm.setForename("Loid");
		userForm.setLastName("Forger");
		userForm.setDob("09/12/1988");
		userForm.setEmail("loid.forger@eden.com");
		userForm.setPassword("logme1nn");
		userForm.setAddress(null);
		
		mock.perform(get("/register").flashAttr("userForm", userForm)).andExpect(view().name("register")).andExpect(status().isOk());
	}
	
	@Test
	public void testInscriptionValidate() throws Exception {
		UserForm userForm = new UserForm();
		User u = new User();
		Address a = new Address(2, "Rue des Sales Clowns", "554-226", "Clown Ville");
		
		userForm.setForename("Loid");
		userForm.setLastName("Forger");
		userForm.setDob("09/12/1988");
		userForm.setEmail("loid.forger@eden.com");
		userForm.setPassword("logme1nn");
		userForm.setAddress(a);
		
		when(userService.register(any(UserForm.class))).thenReturn(u);
		
		mock.perform(post("/register/validate").flashAttr("userForm", userForm)).andExpect(redirectedUrl("/"));
	}
}
