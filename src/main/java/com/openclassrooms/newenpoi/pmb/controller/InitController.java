package com.openclassrooms.newenpoi.pmb.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.openclassrooms.newenpoi.pmb.business.Account;
import com.openclassrooms.newenpoi.pmb.business.Address;
import com.openclassrooms.newenpoi.pmb.business.Payment;
import com.openclassrooms.newenpoi.pmb.business.User;
import com.openclassrooms.newenpoi.pmb.dao.AccountDao;
import com.openclassrooms.newenpoi.pmb.dao.AddressDao;
import com.openclassrooms.newenpoi.pmb.dao.PaymentDao;
import com.openclassrooms.newenpoi.pmb.dao.UserDao;

import lombok.AllArgsConstructor;

@Component
@Order(1)
@AllArgsConstructor
public class InitController implements CommandLineRunner {
	
	private final static int USER_AMOUNT = 3;
	private final static FakeValuesService fakeValuesService = new FakeValuesService(new Locale("fr-FR"), new RandomService());
	private final Faker faker = new Faker(new Locale("fr-FR"));
	
	private final AddressDao addressDao;
	private final AccountDao accountDao;
	private final UserDao userDao;
	private final PaymentDao paymentDao;
	
	@Override
	public void run(String... args) throws Exception {
		
		// Créér des adresses bidon.
		if (addressDao.count() == 0) generateAddresses();
		
		// Créér des comptes bidon.
		if (accountDao.count() == 0) generateAccounts();
		
		// Créér des utilisateurs bidon.
		if (userDao.count() == 0) generateUsers();
		
		// Générer des paiements bidon.
		if (paymentDao.count() == 0) generatePayments();
	}
	
	public void generateAccounts() {
		accountDao.save(new Account(faker.number().numberBetween(100000, 800000), faker.number().randomDouble(2, 5, 3000)));
		accountDao.save(new Account(faker.number().numberBetween(100000, 800000), faker.number().randomDouble(2, 5, 3000)));
	}
	
	public void generateAddresses() {		
		addressDao.save(new Address(faker.number().numberBetween(1, 3000), faker.address().streetName(), faker.address().zipCode(), faker.address().city()));
		addressDao.save(new Address(faker.number().numberBetween(1, 3000), faker.address().streetName(), faker.address().zipCode(), faker.address().city()));
		addressDao.save(new Address(faker.number().numberBetween(1, 3000), faker.address().streetName(), faker.address().zipCode(), faker.address().city()));
	}
	
	public void generateUsers() {
		
		System.out.println("Generating users...");
		
		List<User> users = new ArrayList<User>();
		
		for (int i = 0; i < USER_AMOUNT; i++) {
			String name = faker.name().lastName();
			String forename = faker.name().firstName();
			String email = String.format("%s.%s@gmail.com", name, forename);
			String password = faker.internet().password();
			LocalDate dob = faker.date().birthday(18, 70).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			double balance = faker.number().randomDouble(2, 1000, 3000);
			
			User u = new User(email, password, name, forename, dob, balance);
			
			u.setAddresses(Arrays.asList(addressDao.findById(Long.valueOf(i + 1)).orElse(null)));
			u.setAccounts(Arrays.asList(accountDao.findById(Long.valueOf(i + 1)).orElse(null)));
			
			users.add(u);
		}
		
		users = userDao.saveAll(users);
		
		// adds contacts for the first user.
		users.get(0).getContacts().add(users.get(1));
		users.get(0).getContacts().add(users.get(2));
		
		userDao.saveAll(users);
	}
	
	public void generatePayments() {
		paymentDao.save(new Payment(34.5, LocalDateTime.of(2023, 4, 11, 12, 30, 16), "Essence", userDao.findById(1L).orElse(null), userDao.findById(2L).orElse(null)));
		paymentDao.save(new Payment(16, LocalDateTime.of(2023, 4, 16, 17, 11, 54), "Note de Restaurant", userDao.findById(1L).orElse(null), userDao.findById(3L).orElse(null)));
	}
}
