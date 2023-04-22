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

    @Column(name = "type_voie")
    private String laneType;

    @Column(name = "numero")
    private int number;

    @Column(name = "rue")
    private String street;

    @Column(name = "supplement_adresse")
    private String extra;

    @ManyToMany(mappedBy = "addresses")
    private List<User> users;
}