package com.example.googledrive.repository;

import com.example.googledrive.entity.File;
import com.example.googledrive.entity.Folder;
import com.example.googledrive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FileRepository extends JpaRepository<File, UUID> {

Optional<File> findByUserAndFolderAndFileName(User user, Folder folder, String fileName);

}
