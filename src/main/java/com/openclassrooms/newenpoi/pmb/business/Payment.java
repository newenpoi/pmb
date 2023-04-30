package com.openclassrooms.newenpoi.pmb.business;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "paiements")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "paiement_id")
	private Long id;
	
	@Min(value = 1, message = "La somme doit être supérieure ou égale à 1.")
    private double amount;

    private LocalDateTime delivered;
    
    private String description;

    @ManyToOne
    @JoinColumn(name = "receveur_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "emetteur_id")
    private User sender;
    
    public Payment(User sender, User receiver) {
    	this.sender = sender;
    	this.receiver = receiver;
    }
    
    public Payment(double amount, LocalDateTime delivered, String description, User sender, User receiver) {
    	this(sender, receiver);
    	
    	this.amount = amount;
    	this.delivered = delivered;
    	this.description = description;
    }
}