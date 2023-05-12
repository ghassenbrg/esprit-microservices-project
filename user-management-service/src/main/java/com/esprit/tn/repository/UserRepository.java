package com.esprit.tn.repository;

import com.esprit.tn.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailIgnoreCase(String email);
    
    User findByUsername(String username);
    
    List<User> findByRole(String role);

}