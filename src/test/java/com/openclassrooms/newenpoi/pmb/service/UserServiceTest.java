package com.openclassrooms.newenpoi.pmb.service;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.openclassrooms.newenpoi.pmb.business.Address;
import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dao.AddressDao;
import com.openclassrooms.newenpoi.pmb.dao.UserDao;
import com.openclassrooms.newenpoi.pmb.dto.UserDTO;
import com.openclassrooms.newenpoi.pmb.dto.UserForm;
import com.openclassrooms.newenpoi.pmb.service.impl.UserServiceImpl;
import com.openclassrooms.newenpoi.pmb.util.DateUtils;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
	private static UserServiceImpl userService;
	
	@Mock
    private static UserDao userDao;
	
	@Mock
    private static AddressDao addressDao;
	
	// Creating a mock of the pageable interface.
    @Mock
    private Pageable pageable;
	
	@BeforeEach
	public void setup() {userService = new UserServiceImpl(userDao, addressDao); }
	
    @Test
    public void testLoadUserByUsername_ExistingUser_ReturnsUserDetails() {
    	// Given.
    	String email = "test@gmail.com";
        String password = "azerty";

        User user = new User(); user.setEmail(email); user.setPassword(password);

        when(userDao.findByEmail(email)).thenReturn(user);

        // When.
        UserDetails userDetails = userService.loadUserByUsername(email);
        
        // Then.
        assertEquals(email, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());

        verify(userDao, times(1)).findByEmail(email);
    }
    
    @Test
    public void testLoadUserByUsername_NonExistingUser_ThrowsUsernameNotFoundException() {
        String email = "test@gmail.com";

        when(userDao.findByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> { userService.loadUserByUsername(email); });

        verify(userDao, times(1)).findByEmail(email);
    }
    
    @Test
    public void testRecupererContacts_ReturnsUserContacts() {
    	// Given.
    	Long idUser = 1L;
    	List<User> expected = Arrays.asList(new User(), new User());
        when(userDao.findContactsByUserId(idUser)).thenReturn(expected);
        
        // When.
        List<User> actual = userService.recupererContacts(idUser);
        
        // Then.
        assertEquals(expected, actual);
        verify(userDao, times(1)).findContactsByUserId(idUser);
    }
    
    @Test
    public void testRecupererPageContacts_ReturnsUserPage() {
    	// Given.
    	Long idUser = 1L;
    	Page<User> expected = new PageImpl<>(Arrays.asList(new User(), new User()));
        when(userDao.findContactsByUserId(idUser, pageable)).thenReturn(expected);
        
        // When.
        Page<User> actual = userService.recupererPageContacts(idUser, pageable);
        
        // Then.
        assertEquals(expected, actual);
        verify(userDao, times(1)).findContactsByUserId(idUser, pageable);
    }
    
    @Test
    public void testAjouterContact_ValidUserAndContact_ReturnsUpdatedUser() {
        // Given.
    	String emailUser = "user@gmail.com";
        String emailContact = "contact@gmail.com";

        User user = new User(), contact = new User();
        user.setEmail(emailUser); contact.setEmail(emailContact);
        
        // Définir les contacts pour éviter une exception levée de type NullPointer.
        user.setContacts(new ArrayList<>());

        when(userDao.findByEmail(emailUser)).thenReturn(user);
        when(userDao.findByEmail(emailContact)).thenReturn(contact);
        when(userDao.save(user)).thenReturn(user);

        // When.
        User updatedUser = userService.ajouterContact(emailUser, emailContact);

        // Then.
        assertEquals(user, updatedUser);
        assertTrue(user.getContacts().contains(contact));

        verify(userDao, times(1)).findByEmail(emailUser);
        verify(userDao, times(1)).findByEmail(emailContact);
        verify(userDao, times(1)).save(user);
    }
    
    @Test
    public void testRegister_ValidUserForm_ReturnsRegisteredUser() {
        // Given.
    	Address address = new Address();
        address.setNumber(128);
        address.setStreet("Main Street");
        address.setZipCode("8800");
        address.setCity("Berlint");
    	
    	UserForm userForm = new UserForm();
        userForm.setEmail("user@gmail.com");
        userForm.setPassword("azerty");
        userForm.setLastName("Forger");
        userForm.setForename("Anya");
        
        // On pourrait écrire un test pour le format de la date (DateTimeParseException).
        userForm.setDob("09/11/2015");
        userForm.setAddress(address);

        when(addressDao.save(address)).thenReturn(address);
        
        // On capture l'élément et on défini un comportement spécifique en utilisant thenAsnwer().
        // Une expression lambda avec `invocation` est l'implémentation personnalisée qui capture un objet User, le modifie puis le renvoie.
        when(userDao.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            
            return savedUser;
        });

        // When.
        User registeredUser = userService.register(userForm);

        // Then.
        assertNotNull(registeredUser);
        assertEquals(userForm.getEmail(), registeredUser.getEmail());
        assertEquals(DateUtils.dobToLocalDate(userForm.getDob()), registeredUser.getDob());
        assertEquals(1, registeredUser.getAddresses().size());
        assertEquals(address, registeredUser.getAddresses().get(0));

        verify(addressDao, times(1)).save(address);
        verify(userDao, times(1)).save(registeredUser);
    }
    
    @Test
    @DisplayName("Modifie l'adresse d'un profil utilisateur.")
    public void testModifierProfil_ValidIdAndUserForm_ReturnsModifiedUser() {
        // Given.
        Long idUser = 1L;
        UserForm userForm = new UserForm();
        
        userForm.setForename("Anya");
        userForm.setLastName("Forger");
        userForm.setDob("09/11/2015");
        
        Address address = new Address();
        address.setStreet("Grand Canal Street");
        
        userForm.setAddress(address);
        
        User user = new User(); user.setId(idUser);
        user.setAddresses(Arrays.asList(address));
        
        when(userDao.findById(idUser)).thenReturn(Optional.of(user));
        when(userDao.save(user)).thenReturn(user);
        
        // When.
        User modifiedUser = userService.modifierProfil(idUser, userForm);
        
        System.out.println(address);
        System.out.println(modifiedUser);
        
        // Then.
        assertNotNull(modifiedUser);
        assertEquals(1, modifiedUser.getAddresses().size());
        assertEquals(address, modifiedUser.getAddresses().get(0));
        
        verify(userDao, times(1)).findById(idUser);
        verify(userDao, times(1)).save(user);
    }
    
    @Test
    public void testRecupererUtilisateur_ValidId_ReturnsUser() {
        // Given.
        Long idUser = 1L;
        User expected = new User();
        
        when(userDao.findById(idUser)).thenReturn(Optional.of(expected));
        
        // When.
        User actual = userService.recupererUtilisateur(idUser);
        
        // Then.
        assertEquals(expected, actual);
        verify(userDao, times(1)).findById(idUser);
    }
    
    @Test
    public void testRecupererUtilisateur_ValidEmail_ReturnsUser() {
        // Given.
        String email = "user@gmail.com";
        User expectedUser = new User();
        
        when(userDao.findByEmail(email)).thenReturn(expectedUser);
        
        // When.
        User retrievedUser = userService.recupererUtilisateur(email);
        
        // Then.
        assertEquals(expectedUser, retrievedUser);
        verify(userDao, times(1)).findByEmail(email);
    }
    
    @Test
    public void testRecupererUtilisateurDTO_ValidEmail_ReturnsUserDTO() {
        // Given.
        String email = "user@gmail.com";
        User user = new User();
        
        when(userDao.findByEmail(email)).thenReturn(user);
        
        // When.
        UserDTO retrievedUserDTO = userService.recupererUtilisateurDTO(email);
        
        // Then.
        assertNotNull(retrievedUserDTO);
        assertEquals(user.getEmail(), retrievedUserDTO.getEmail());
        
        verify(userDao, times(1)).findByEmail(email);
    }
    
    @Test
    @Disabled
    public void testSupprimerContact_ReturnsUpdatedUser() {
        // Given.
    	// TODO : Il manque des propriétés à définir.
        User contact = new User();
        contact.setId(1L);

        User user = new User();
        user.setId(2L);
        user.setContacts(Arrays.asList(contact));

        when(userDao.findById(1L)).thenReturn(Optional.of(contact));
        when(userDao.save(user)).thenReturn(user);

        // When.
        User updatedUser = userService.supprimerContact(user, 1L);

        // Then.
        assertNotNull(updatedUser);
        assertFalse(updatedUser.getContacts().contains(contact));
        verify(userDao).findById(1L);
        verify(userDao).save(user);
    }
}
