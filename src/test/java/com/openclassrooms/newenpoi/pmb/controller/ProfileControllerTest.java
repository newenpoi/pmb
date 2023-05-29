package com.openclassrooms.newenpoi.pmb.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
public class ProfileControllerTest {
	
    private MockMvc mock;
    
    @Autowired
	private ProfileController profileController;
	
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
		mock = MockMvcBuilders.standaloneSetup(profileController).build();
		
		// Nécesaire dans le contexte d'authentification (applique un `configurer` dans notre cas spécifique).
		this.mock = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
	}
	
	@Test
	@DisplayName("Teste la récupération du profil d'un utilisateur authentifié.")
	public void testGetProfile_shouldReturnExtendedUserDTO_ofAuthenticatedUser() throws Exception {
		User u = new User();
		u.setAddresses(Arrays.asList(new Address()));
		
		when(userService.recupererUtilisateur("loid.forger@eden.com")).thenReturn(u);
		
		mock.perform(get("/profile").with(user("loid.forger@eden.com").password("logme1nn"))).andExpect(view().name("profile")).andExpect(model().attributeExists("user")).andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Teste la modification du profil d'un utilisateur authentifié.")
	public void testSaveProfile_shouldReturnModifiedExtendedUserDTO_ofAuthenticatedUser() throws Exception {
		User u = new User(), modifiedUser = new User();
		List<Address> addresess = Arrays.asList(new Address(2, "Rue des Sales Clowns", "652-332", "Clown Land"));
		
		u.setAddresses(Arrays.asList(new Address()));
		u.setId(1L);
		
		modifiedUser.setAddresses(addresess);
		modifiedUser.setId(1L);
		
		UserForm userForm = new UserForm();
		
		userForm.setForename("Loid");
		userForm.setLastName("Forger");
		userForm.setDob("09/12/1988");
		userForm.setEmail("loid.forger@eden.com");
		userForm.setPassword("logme1nn");
		userForm.setAddress(addresess.get(0));
		
		when(userService.recupererUtilisateur("loid.forger@eden.com")).thenReturn(u);
		when(userService.modifierProfil(any(Long.class), any(UserForm.class))).thenReturn(modifiedUser);
		
        mock.perform(post("/profile/update").flashAttr("userForm", userForm).with(user("loid.forger@eden.com").password("logme1nn"))).andExpect(redirectedUrl("/profile"));
	}
}
