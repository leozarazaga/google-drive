package com.example.googledrive.repository;

import com.example.googledrive.entity.File;
import com.example.googledrive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing File entities in the database.
 * Extends JpaRepository to provide CRUD operations on File entities.
 */
public interface FileRepository extends JpaRepository<File, UUID> {
    List<File> findByUser(User user);
    Optional<File> findByIdAndUser(UUID id, User user);
}
