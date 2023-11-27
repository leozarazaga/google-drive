package com.example.googledrive.repository;

import com.example.googledrive.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FolderRepository extends JpaRepository<Folder, UUID> {
    List<Folder> findByFolderNameContainingIgnoreCase(String name);
}
