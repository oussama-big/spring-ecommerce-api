package com.codewithmosh.store.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithmosh.store.users.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
  
    Optional<User> findById(Long id);
}
