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
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

/**
 * Service responsible for managing folder-related operations.
 * Handles folder creation, retrieval, updating, and deletion, as well as other folder-related functionalities.
 */
@Transactional
@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final UserRepository userRepository;


    @Autowired
    public FolderService(FolderRepository folderRepository, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new folder based on the provided folder details.
     *
     * @param dto The details of the folder to be created.
     * @return The newly created folder.
     */
    public Folder createFolder(CreateFolderDto dto) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new UserNotFoundException(currentUsername));

        Folder folder = new Folder(dto.getFolderName());
        folder.setUser(currentUser);
        return folderRepository.save(folder);
    }

    /**
     * Retrieves a list of all folders associated with a specific user.
     *
     * @param user The user for whom to retrieve folders.
     * @return A list of folders belonging to the user.
     */
    public List<Folder> getAllFoldersByUser(User user) {
        return folderRepository.findByUser(user);
    }

    /**
     * Retrieves a specific folder by its unique identifier and associated user.
     *
     * @param id   The unique identifier of the folder.
     * @param user The user associated with the folder.
     * @return The folder with the specified identifier.
     * @throws FolderNotFoundException if the folder is not found.
     */
    public Folder getFolderById(String id, User user) {
        UUID uuid = UUID.fromString(id);

        Folder folder = folderRepository.findByIdAndUser(uuid, user)
                .orElseThrow(() -> new FolderNotFoundException(id));

        return folder;
    }


    /**
     * Retrieves a list of files associated with a specific folder and user.
     *
     * @param folderId The unique identifier of the folder.
     * @param user     The user associated with the folder.
     * @return A list of files within the folder.
     * @throws AccessDeniedException if the user does not have access to the folder.
     */
    public List<File> getFilesFromFolder(String folderId, User user) {
        UUID uuid = UUID.fromString(folderId);
        Folder folder = folderRepository.findByIdAndUser(uuid, user)
                .orElseThrow(() -> new AccessDeniedException("You do not have access to this folder"));

        return folderRepository.findFilesByFolderIdAndUser(uuid, user);
    }

    /**
     * Searches for folders with names containing the specified search term associated with a specific user.
     *
     * @param search The search term for folder names.
     * @param user   The user associated with the folders.
     * @return A list of folders matching the search criteria.
     * @throws NoSearchResultFoundException if no matching folders are found.
     */
    public List<Folder> searchByFolderName(String search, User user) {
        List<Folder> findByFolderName = folderRepository.findByFolderNameContainingIgnoreCaseAndUser(search, user);

        if (findByFolderName.isEmpty()) {
            throw new NoSearchResultFoundException(search);
        }
        return findByFolderName;
    }

    /**
     * Updates the details of an existing folder.
     *
     * @param id   The unique identifier of the folder to be updated.
     * @param dto  The updated details of the folder.
     * @param user The user associated with the folder.
     * @return The updated folder.
     * @throws FolderNotFoundException if the folder is not found.
     */
    public Folder updateFolder(String id, UpdateFolderDto dto, User user) {
        UUID uuid = UUID.fromString(id);
        Folder folder = folderRepository.findByIdAndUser(uuid, user).orElseThrow(() -> new FolderNotFoundException(id));

        if (dto.getFolderName().isPresent()) {
            folder.setFolderName(dto.getFolderName().get());
        }
        return folderRepository.save(folder);
    }

    /**
     * Deletes a folder by its unique identifier and associated user.
     *
     * @param id   The unique identifier of the folder to be deleted.
     * @param user The user associated with the folder.
     * @throws FolderNotFoundException if the folder is not found.
     */
    public void deleteFolder(String id, User user) {
        UUID uuid = UUID.fromString(id);
        Folder folder = folderRepository.findByIdAndUser(uuid, user).orElseThrow(() -> new FolderNotFoundException(id));
        folderRepository.delete(folder);
    }
}
