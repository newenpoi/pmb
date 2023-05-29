package com.openclassrooms.newenpoi.pmb.business;

import java.time.LocalDate;
import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "utilisateur")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "utilisateur_id")
    private Long id;
	
	@NotBlank(message = "Une adresse email est requise.")
	@Column(unique = true)
	private String email;

	@Column(name = "mot_de_passe")
	@NotBlank(message = "Le mot de passe est requis.")
	private String password;

	@Column(name = "nom")
	@NotBlank(message = "Le nom de famille est requis.")
	private String name;

	@Column(name = "prenom")
	@NotBlank(message = "Le prénom est requis.")
	private String forename;

	@Column(name = "date_naissance")
	@PastOrPresent(message = "La date de naissance doit être dans le passé.")
	private LocalDate dob;
	
	@PositiveOrZero
	private double balance;

	@ToString.Exclude
	@ManyToMany
	@JoinTable(name = "utilisateurs_contacts", joinColumns = @JoinColumn(name = "utilisateur_id"), inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private List<User> contacts;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "utilisateurs_adresses", joinColumns = @JoinColumn(name = "utilisateur_id"), inverseJoinColumns = @JoinColumn(name = "adresse_id"))
    private List<Address> addresses;

	@ToString.Exclude
	@OneToMany(mappedBy = "sender")
    private List<Payment> madePayments;

	@ToString.Exclude
	@OneToMany(mappedBy = "receiver")
    private List<Payment> receivedPayments;

	@ToString.Exclude
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Account> accounts;
	
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

	public User(String email, String password, String name, String forename, LocalDate dob, double balance) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.forename = forename;
		this.dob = dob;
		this.balance = balance;
	}
}