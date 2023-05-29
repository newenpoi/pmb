package com.openclassrooms.newenpoi.pmb.business;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comptes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "compte_id")
	private Long id;

	/**
	 * Considère dans le cadre de l'exercice que ceci est par exemple son iban.
	 */
	@Column(name = "numero_compte", unique = true)
    private int accountNumber;
	
	private String label;

	@Enumerated(EnumType.STRING)
	@Column(name = "compte_type")
    private AccountType accountType;

	@Column(name = "solde")
    private double balance;

    @ManyToOne()
    @JoinColumn(name = "utilisateur_id")
    private User user;
    
    public Account(int accountNumber, double balance) {
    	this.accountType = AccountType.CHECKING;
    	
    	this.accountNumber = accountNumber;
    	this.balance = balance;
    }
}