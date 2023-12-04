package com.example.googledrive.repository;

import com.example.googledrive.entity.File;
import com.example.googledrive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface FileRepository extends JpaRepository<File, UUID> {
    @Transactional
    List<File> findByUser(User user);


}
