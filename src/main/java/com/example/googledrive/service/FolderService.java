package com.example.googledrive.service;

import com.example.googledrive.dto.CreateFolderDto;
import com.example.googledrive.dto.UpdateFolderDto;
import com.example.googledrive.entity.File;
import com.example.googledrive.entity.Folder;
import com.example.googledrive.entity.User;
import com.example.googledrive.exception.FolderNotFoundException;
import com.example.googledrive.exception.NoSearchResultFoundException;
import com.example.googledrive.exception.UserNotFoundException;
import com.example.googledrive.repository.FolderRepository;
import com.example.googledrive.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final UserRepository userRepository;


    @Autowired
    public FolderService(FolderRepository folderRepository, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
    }

    /*    - - - - - - - - - - - - - - - - - - -   */


    public Folder createFolder(CreateFolderDto dto) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UserNotFoundException(currentUsername));

        Folder folder = new Folder(dto.getFolderName());
        folder.setUser(currentUser);
        return folderRepository.save(folder);
    }

    public List<Folder> getAllFoldersByUser(User user) {
        return folderRepository.findByUser(user);
    }


    public Folder getFolderById(String id, User user) {
        UUID uuid = UUID.fromString(id);

        Folder folder = folderRepository.findByIdAndUser(uuid, user)
                .orElseThrow(() -> new FolderNotFoundException(id));

        return folder;
    }

    //@Transactional
    public List<File> getFilesFromFolder(String folderId, User user) {
        UUID uuid = UUID.fromString(folderId);
        Folder folder = folderRepository.findByIdAndUser(uuid, user)
                .orElseThrow(() -> new FolderNotFoundException(folderId));

        // Ensure that files are loaded
        List<File> files = folder.getFiles();

        // Check if the authenticated user owns the folder
        if (!folder.getUser().equals(user)) {
            throw new AccessDeniedException("You do not have permission to access files in this folder");
        }

        return files;
    }

    @Transactional
    public List<Folder> searchByFolderName(String search, User user) {
        List<Folder> findByFolderName = folderRepository.findByFolderNameContainingIgnoreCaseAndUser(search, user);

        if (findByFolderName.isEmpty()) {
            throw new NoSearchResultFoundException(search);
        }
        return findByFolderName;
    }

    public Folder updateFolder(String id, UpdateFolderDto dto, User user) {
        UUID uuid = UUID.fromString(id);
        Folder folder = folderRepository.findByIdAndUser(uuid, user).orElseThrow(() -> new FolderNotFoundException(id));

        if (dto.getFolderName().isPresent()) {
            folder.setFolderName(dto.getFolderName().get());
        }
        return folderRepository.save(folder);
    }

    public void deleteFolder(String id, User user) {
        UUID uuid = UUID.fromString(id);
        Folder folder = folderRepository.findByIdAndUser(uuid, user).orElseThrow(() -> new FolderNotFoundException(id));
        folderRepository.delete(folder);
    }
}
