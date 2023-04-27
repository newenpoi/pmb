package com.openclassrooms.newenpoi.pmb.business;

import java.time.LocalDate;
import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "utilisateur")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "utilisateur_id")
    private Long userId;
	
	@NotBlank(message = "Une adresse email est requise.")
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

	@ManyToMany
	@JoinTable(name = "utilisateurs_contacts", joinColumns = @JoinColumn(name = "utilisateur_id"), inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private List<User> contacts;

	@ManyToMany
	@JoinTable(name = "utilisateurs_adresses", joinColumns = @JoinColumn(name = "utilisateur_id"), inverseJoinColumns = @JoinColumn(name = "adresse_id"))
    private List<Address> addresses;

	@OneToMany(mappedBy = "sender")
    private List<Payment> madePayments;

	@OneToMany(mappedBy = "receiver")
    private List<Payment> receivedPayments;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Account> accounts;

	public User(String email, String password, String name, String forename, LocalDate dob) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.forename = forename;
		this.dob = dob;
	}
}