package com.openclassrooms.newenpoi.pmb.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dao.UserDao;
import com.openclassrooms.newenpoi.pmb.service.UserService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactControllerTest {
	
    private MockMvc mock;
    
    @Autowired
	private ContactController contactController;
	
	@Autowired
	private WebApplicationContext context;
	
    @Mock
    private UserDetailsService userDetailsService;
    
    @MockBean
    private UserService userService;
    
    @Mock
    private UserDao userDao;
	
	/**
	 * On isole le contrôleur sans charger la totalité du contexte de l'application.
	 */
	@BeforeEach
	public void setup() {
		mock = MockMvcBuilders.standaloneSetup(contactController).build();
		
		// Nécesaire dans le contexte d'authentification (applique un `configurer` dans notre cas spécifique).
		this.mock = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
	}
	
    @Test
    public void testGetContacts() throws Exception {
		User u = new User();
		u.setId(1L);
		u.setName("Forger");
    	
    	List<User> contacts = Arrays.asList(new User());
		PageImpl<User> page = new PageImpl<>(contacts);
		
		when(userService.recupererUtilisateur("loid.forger@eden.com")).thenReturn(u);
		when(userService.recupererPageContacts(anyLong(), any(Pageable.class))).thenReturn(page);
    	
    	mock.perform(get("/contacts").with(user("loid.forger@eden.com").password("logme1nn"))).andExpect(view().name("contacts")).andExpect(model().attributeExists("contacts")).andExpect(status().isOk());
    }
    
    @Test
    public void testAddContact() throws Exception {
        User contact = new User("anya.forger@eden.com", "azerty", "Forger", "Anya", LocalDate.now());
    	
    	// Arrange.
        when(userService.ajouterContact(anyString(), anyString())).thenReturn(contact);

        // Act and Assert.
        mock.perform(post("/contacts/add").param("email", "anya.forger@eden.com").with(user("loid.forger@eden.com").password("logme1nn"))).andExpect(redirectedUrl("/contacts"));
    }
}
