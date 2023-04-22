package com.openclassrooms.newenpoi.pmb.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.newenpoi.pmb.business.User;

public interface UserDao extends JpaRepository<User, Long> {

}
