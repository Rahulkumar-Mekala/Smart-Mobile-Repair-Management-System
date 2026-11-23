package com.smartmobile.managementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartmobile.managementsystem.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByGoogleId(String googleId);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}