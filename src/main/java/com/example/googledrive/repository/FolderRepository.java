package com.example.googledrive.repository;

import com.example.googledrive.entity.Folder;
import com.example.googledrive.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FolderRepository extends JpaRepository<Folder, UUID> {
    List<Folder> findByFolderNameContainingIgnoreCaseAndUser(String search, User user);

    List<Folder> findByUser(User user);

    Optional<Folder> findByIdAndUser(UUID id, User user);



}
