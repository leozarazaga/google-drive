package com.example.googledrive.repository;

import com.example.googledrive.entity.File;
import com.example.googledrive.entity.Folder;
import com.example.googledrive.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Folder entities in the database.
 * Extends JpaRepository to provide CRUD operations on Folder entities.
 */
public interface FolderRepository extends JpaRepository<Folder, UUID> {
    List<Folder> findByFolderNameContainingIgnoreCaseAndUser(String search, User user);
    List<Folder> findByUser(User user);
    Optional<Folder> findByIdAndUser(UUID id, User user);
    @Query("SELECT f.files FROM Folder f WHERE f.id = :folderId AND f.user = :user")
    List<File> findFilesByFolderIdAndUser(@Param("folderId") UUID folderId, @Param("user") User user);
}
