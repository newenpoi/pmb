package com.openclassrooms.newenpoi.pmb.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.newenpoi.pmb.business.Address;

import jakarta.transaction.Transactional;

@Transactional
public interface AddressDao extends JpaRepository<Address, Long> {

}
