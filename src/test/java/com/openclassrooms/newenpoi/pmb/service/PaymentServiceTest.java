package com.openclassrooms.newenpoi.pmb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.openclassrooms.newenpoi.pmb.business.Account;
import com.openclassrooms.newenpoi.pmb.business.Payment;
import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dao.PaymentDao;
import com.openclassrooms.newenpoi.pmb.dao.UserDao;
import com.openclassrooms.newenpoi.pmb.service.impl.PaymentServiceImpl;
import com.openclassrooms.newenpoi.pmb.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
	
	private static PaymentServiceImpl paymentService;
	
	@Mock private static PaymentDao paymentDao;
	
	@Mock private static UserDao userDao;
	
	// Creating a mock of the pageable interface.
    @Mock
    private Pageable pageable;
    
	@BeforeEach
	public void setup() {paymentService = new PaymentServiceImpl(paymentDao, userDao); }
	
	@Test
	public void testRecupererPaiements_ReturnsPaymentPage() {
	    // Given.
	    User user = new User();
	    Pageable pageable = PageRequest.of(0, 10);
	    List<Payment> expected = new ArrayList<>();
	    
	    when(paymentDao.findBySenderOrReceiver(eq(user), eq(user), eq(pageable))).thenReturn(new PageImpl<>(expected));
	    
	    // When.
	    Page<Payment> actual = paymentService.recupererPaiements(user, pageable);
	    
	    // Then.
	    assertNotNull(actual);
	    assertEquals(expected, actual.getContent());
	}
	
	@Test
	@Disabled
	public void testPayer_shouldReturnPayment() {
		fail("Not implemented yet...");
	}
	
	/**
	 * On va séparer les tests en de petites unités de tests.
	 */
    @Test
    public void testPayer_InsufficientBalance_ReturnsNull() {
        // Given.
        User sender = createUserWithBalance(100.0);
        User receiver = createUserWithBalance(0.0);
        double sum = 200.0;
        
        // When.
        Payment payment = paymentService.payer(sender, receiver.getId(), "Lesson de Java", sum);
        
        // Then.
        assertNull(payment);
    }
    
    @Test
    public void testPayer_ValidPayment_ReturnsPayment() {
        // Given.
    	User sender = createUserWithBalance(100.0);
        User receiver = createUserWithBalance(50.0);
        Payment p = new Payment(20, LocalDateTime.now(), "Gazole", sender, receiver);
        Optional<User> receiverOptional = Optional.of(receiver);
        
        when(paymentDao.save(any(Payment.class))).thenReturn(p);
        when(userDao.findById(7L)).thenReturn(receiverOptional);
        
        // When.
        Payment payment = paymentService.payer(sender, 7L, "Gazole", 20);
        
        // Then.
        assertNotNull(payment);
        assertEquals(20, payment.getAmount(), 0.01);
        assertNotNull(payment.getDelivered());
        assertEquals(sender, payment.getSender());
        assertEquals(receiver, payment.getReceiver());
    }
    
    /**
     * Renvoie un objet utilisateur dans le cadre des tests.
     * @param balance
     * @return
     */
    private User createUserWithBalance(double balance) {
        Account account = new Account();
        account.setBalance(balance);
        List<Account> accounts = Collections.singletonList(account);
        User user = new User();
        user.setAccounts(accounts);
        return user;
    }
}
