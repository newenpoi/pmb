package com.openclassrooms.newenpoi.pmb.business;

import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "adresses")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "adresse_id")
	private Long id;

    @Column(name = "numero")
    private int number;

    @Column(name = "rue")
    private String street;

    @Column(name = "supplement_adresse")
    private String extra;
    
    @Column(name = "code_postal")
    private String zipCode;
    
    @Column(name = "ville")
    private String city;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users;
    
    public Address(int number, String street, String zipCode, String city) {
    	this.number = number;
    	this.street = street;
    	this.zipCode = zipCode;
    	this.city = city;
    }
}