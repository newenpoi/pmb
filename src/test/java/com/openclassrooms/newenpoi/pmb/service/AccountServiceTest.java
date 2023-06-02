package com.openclassrooms.newenpoi.pmb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.newenpoi.pmb.business.Account;
import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dao.AccountDao;
import com.openclassrooms.newenpoi.pmb.dao.UserDao;
import com.openclassrooms.newenpoi.pmb.service.impl.AccountServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
	
	@Mock private static UserDao userDao;
	
	@Mock private static AccountDao accountDao;
	
	private static AccountServiceImpl accountService;
	
	@BeforeEach
	public void setup() {accountService = new AccountServiceImpl(accountDao, userDao); }
	
	@Test
	public void testRecupererComptes_shouldReturnListOfAccount_ofUser() {
	    // Given.
	    User u = new User();
		List<Account> expected = Arrays.asList(new Account("FR55-226A-2000-2000-2000", "Personal", 2000));
	    
	    when(accountDao.findByUser(u)).thenReturn(expected);
	    
	    // When.
	    List<Account> actual = accountService.recupererComptes(u);
	    
	    // Then.
	    assertNotNull(actual);
	    assertEquals(expected, actual);
	    verify(accountDao).findByUser(u);
	}
	
	@Test
	public void testTranferer_shouldReturnSavecAccount_ofUser() {
        // Given.
        User user = new User();
        user.setBalance(1000.0);
        
        Account account = new Account();
        account.setBalance(500.0);
        account.setId(2L);
        
        user.setAccounts(Arrays.asList(account));

        Account savedAccount = new Account();
        savedAccount.setBalance(account.getBalance() + (800 * 0.95));
        
        // When.
        when(accountDao.findById(anyLong())).thenReturn(Optional.of(account));
        when(userDao.save(any(User.class))).thenReturn(user);
        when(accountDao.save(any(Account.class))).thenReturn(savedAccount);

        // Then.
        Account actualAccount = accountService.transferer(user, 800);

        assertEquals(savedAccount.getBalance(), actualAccount.getBalance(), 0.01);
        verify(accountDao).findById(anyLong());
        verify(userDao).save(any(User.class));
        verify(accountDao).save(any(Account.class));
	}
}
