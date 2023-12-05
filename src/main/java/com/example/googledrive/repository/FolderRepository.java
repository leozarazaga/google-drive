package com.example.googledrive.repository;

import com.example.googledrive.entity.Folder;
import com.example.googledrive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FolderRepository extends JpaRepository<Folder, UUID> {
    List<Folder> findByFolderNameContainingIgnoreCase(String name);

    List<Folder> findByUser(User user);
    Optional<Folder> findByIdAndAndUser(UUID id, User user);
}
