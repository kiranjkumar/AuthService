package com.java.authService.repositories;

import com.java.authService.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRespository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
