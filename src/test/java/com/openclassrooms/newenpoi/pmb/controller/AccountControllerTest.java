package com.openclassrooms.newenpoi.pmb.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import com.openclassrooms.newenpoi.pmb.business.Account;
import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dao.AccountDao;
import com.openclassrooms.newenpoi.pmb.dao.UserDao;
import com.openclassrooms.newenpoi.pmb.service.AccountService;
import com.openclassrooms.newenpoi.pmb.service.UserService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountControllerTest {
    private MockMvc mock;
    
    @Autowired
	private AccountController accountController;
	
	@Autowired
	private WebApplicationContext context;
	
    @Mock
    private UserDetailsService userDetailsService;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private AccountService accountService;
    
    @Mock
    private AccountDao accountDao;
	
	/**
	 * On isole le contrôleur sans charger la totalité du contexte de l'application.
	 */
	@BeforeEach
	public void setup() {
		mock = MockMvcBuilders.standaloneSetup(accountController).build();
		
		// Nécesaire dans le contexte d'authentification (applique un `configurer` dans notre cas spécifique).
		this.mock = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
	}
	
    @Test
    public void testAccountGet() throws Exception {
		User u = new User();
    	List<Account> accounts = Arrays.asList(new Account());
		
    	when(userService.recupererUtilisateur("loid.forger@eden.com")).thenReturn(u);
		when(accountService.recupererComptes(any(User.class))).thenReturn(accounts);
    	
    	mock.perform(get("/accounts").with(user("loid.forger@eden.com").password("logme1nn"))).andExpect(view().name("accounts")).andExpect(model().attributeExists("accounts")).andExpect(status().isOk());
    }
}
