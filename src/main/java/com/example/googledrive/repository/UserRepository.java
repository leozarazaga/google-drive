package com.example.googledrive.repository;

import com.example.googledrive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing User entities in the database.
 * Extends JpaRepository to provide CRUD operations on User entities.
 */
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

}
